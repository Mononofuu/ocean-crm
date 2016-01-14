package com.becomejavasenior.impl;


import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactDAOImpl extends AbstractJDBCDao<Contact> implements ContactDAO {
    private Logger logger = LogManager.getLogger(ContactDAOImpl.class);

    @Override
    protected String getConditionStatment() {
        return "WHERE contact.id = ?";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT contact.id, post, phone_type_id, phone, email, skype, company_id, name  FROM contact JOIN subject ON subject.id=contact.id ";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM contact WHERE id= ?;";
    }

    @Override
    protected List<Contact> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            GenericDao phoneTypeDao = getDaoFromCurrentFactory(PhoneType.class);
            GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
            SubjectTagDAOImpl subjectTagDAOImpl = (SubjectTagDAOImpl) getDaoFromCurrentFactory(SubjectTag.class);
            while (rs.next()) {
                Contact contact = new Contact();
                int id = rs.getInt("id");
                // Считываем данные из таблицы subject
                Subject subject = (Subject) subjectDao.read(id);
                contact.setId(id);
                contact.setName(subject.getName());
                contact.setPost(rs.getString("post"));
                // Считываем данные из таблицы phone_type
                PhoneType phoneType = (PhoneType) phoneTypeDao.read(rs.getInt("phone_type_id"));
                contact.setPhoneType(phoneType);
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSkype(rs.getString("skype"));
                // Считываем данные из таблицы company
                Company company = (Company) companyDao.read(rs.getInt("company_id"));
                contact.setCompany(company);
                // Считываем тэги
                contact.setTags(subjectTagDAOImpl.getAllTagsBySubjectId(id));
                result.add(contact);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    public ContactDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    protected List<Contact> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Contact contact = new Contact();
                int id = rs.getInt("id");
                contact.setId(id);
                contact.setName(rs.getString("name"));
                contact.setPost(rs.getString("post"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSkype(rs.getString("skype"));
                result.add(contact);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO contact (id, post, phone_type_id, phone, email, skype, company_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Contact object) throws DataBaseException {
        try {
            statement.setInt(1, createSubject(object));
            statement.setString(2, object.getPost());
            if(object.getPhoneType()!=null){
                statement.setInt(3, object.getPhoneType().ordinal() + 1);
            }else{
                statement.setNull(3, Types.INTEGER);
            }
            statement.setString(4, object.getPhone());
            statement.setString(5, object.getEmail());
            statement.setString(6, object.getSkype());
            if(object.getCompany()!=null){
                statement.setInt(7, object.getCompany().getId());
            }else{
                statement.setNull(7, Types.INTEGER);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE contact SET post = ?, phone_type_id = ?, phone = ?, email = ?, skype = ?, company_id = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Contact object) throws DataBaseException {
        try{
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
            statement.setString(1, object.getPost());
            statement.setInt(2, object.getPhoneType().ordinal() + 1);
            statement.setString(3, object.getPhone());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getSkype());
            statement.setInt(6, object.getCompany().getId());
            statement.setInt(7, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public void delete(int id) throws DataBaseException {
        GenericDao<Subject> subjectDao = getDaoFromCurrentFactory(Subject.class);
        subjectDao.delete(id);
    }

    @Override
    public Contact readContactByName(String name) throws DataBaseException {
        Contact result;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery()+" WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            List<Contact> allObjects = parseResultSetLite(rs);
            if (allObjects.size() == 0) {
                return null;
            }
            result = allObjects.get(0);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public List<Contact> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, Date taskStartDate, Date taskDueDate) throws DataBaseException {
        return realiseQuery(getParametrisedReadQuery(parameters, userId, tagIdList, taskStartDate, taskDueDate));
    }

    private String getParametrisedReadQuery(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, Date taskStartDate, Date taskDueDate){
        String result = getReadAllQuery();
        if(parameters!=null&&parameters.size()>0){
            if(parameters.contains(ContactFilters.WITHOUT_TASKS)){
                result+=getContactsWithoutTasksQuery();
            }
            if(parameters.contains(ContactFilters.WITH_OVERDUE_TASKS)){
                result+=getContactsWithOverdueTasksQuery();
            }
            if(parameters.contains(ContactFilters.WITHOUT_DEALS)){
                result+=getContactsWithoutDealsQuery();
            }
            if(parameters.contains(ContactFilters.WITHOUT_OPEN_DEALS)){
                result+=getContactsWithoutOpenDealsQuery();
            }
            if(parameters.contains(ContactFilters.PRIMARY_CONTACTS)){
                result+=getPrimaryContactsQuery();
            }
            if(parameters.contains(ContactFilters.CONVERSATION_CONTACTS)){
                result+=getConversationContactsQuery();
            }
            if(parameters.contains(ContactFilters.MAKING_DECISION_CONTACTS)){
                result+=getMakingDecisionContactsQuery();
            }
            if(parameters.contains(ContactFilters.SUCCESS_CONTACTS)){
                result+=getSuccessContactsQuery();
            }
            if(parameters.contains(ContactFilters.NOT_REALISED_CONTACTS)){
                result+=getNotRealisedContactsQuery();
            }
        }
        result+=" WHERE 1=1";
        if(userId!=null){
            result+=" AND user_id = "+userId;
        }
        if(tagIdList!=null&&tagIdList.size()>0){
            result+=getContactsByTagsQuery(tagIdList);
        }
        if(taskStartDate!=null&&taskDueDate!=null){
            result+=getContactsByTaskPeriod(taskStartDate, taskDueDate);
        }

        return result;
    }

    private String getContactsWithoutTasksQuery(){
        return " LEFT JOIN task ON contact.id=task.subject_id WHERE subject_id is NULL";
    }

    private String getContactsWithOverdueTasksQuery(){
        return " LEFT JOIN task ON contact.id=task.subject_id WHERE task.due_date < NOW()";
    }

    private String getContactsWithoutDealsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.contact_main_id is NULL";
    }

    private String getContactsWithoutOpenDealsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id ON (5, 6)";
    }

    private String getPrimaryContactsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id = 1";
    }

    private String getConversationContactsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id = 2";
    }

    private String getMakingDecisionContactsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id = 3";
    }

    private String getApprovalContractContactsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id = 4";
    }

    private String getSuccessContactsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id = 5";
    }

    private String getNotRealisedContactsQuery(){
        return " LEFT JOIN task ON contact.id=deal.contact_main_id WHERE deal.status_id = 6";
    }

    private String getContactsByTagsQuery(List<Integer> tagIdList){
        String result = " JOIN subject_tag ON contact.id=subject_tag.subject_id WHERE subject_tag.subject_id ON (";
        for(Integer id: tagIdList){
            result+=id+",";
        }
        result.substring(0, result.length()-1);
        result+=")";
        return result;
    }

    private String getContactsByTaskPeriod(Date taskStartDate, Date taskDueDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  " LEFT JOIN task ON contact.id=task.subject_id WHERE task.due_date BETWEEN '"+dateFormat.format(taskStartDate)+"' AND '"+dateFormat.format(taskDueDate)+"'";
    }
}
