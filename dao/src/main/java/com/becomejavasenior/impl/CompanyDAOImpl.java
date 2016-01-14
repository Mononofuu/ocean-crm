package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl extends AbstractJDBCDao<Company> implements CompanyDAO {
    private Logger logger = LogManager.getLogger(CompanyDAOImpl.class);

    public CompanyDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    protected String getConditionStatment() {
        return "WHERE company.id = ?";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT company.id, phone_number, email, web, address, name  FROM company JOIN subject ON subject.id=company.id ";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM company WHERE id= ?;";
    }

    @Override
    protected List<Company> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Company> result = new ArrayList<>();
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            while (rs.next()) {
                Company company = new Company();
                Subject subject = (Subject) subjectDao.read(rs.getInt("id"));
                company.setId(subject.getId());
                company.setUser(subject.getUser());
                company.setName(subject.getName());
                company.setPhoneNumber(rs.getString("phone_number"));
                company.setEmail(rs.getString("email"));
                try {
                    company.setWeb(new URL(rs.getString("web")));
                } catch (MalformedURLException e) {
                    /*NOP*/
                }
                company.setAdress(rs.getString("address"));
                result.add(company);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected List<Company> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Company> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                company.setPhoneNumber(rs.getString("phone_number"));
                company.setEmail(rs.getString("email"));
                try {
                    company.setWeb(new URL(rs.getString("web")));
                } catch (MalformedURLException e) {
                    /*NOP*/
                }
                company.setAdress(rs.getString("address"));
                result.add(company);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO company (id, phone_number, email, web, address) VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Company object) throws DataBaseException {
        try {
            statement.setInt(1, createSubject(object));
            statement.setString(2, object.getPhoneNumber());
            statement.setString(3, object.getEmail());
            if(object.getWeb()!=null){
                statement.setString(4, object.getWeb().toString());
            }else{
                statement.setNull(4, Types.CHAR);
            }
            statement.setString(5, object.getAdress());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE company SET phone_number = ?, email = ?, web = ?, address = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Company object) throws DataBaseException {
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
            statement.setString(1, object.getPhoneNumber());
            statement.setString(2, object.getEmail());
            statement.setString(3, object.getWeb().toString());
            statement.setString(4, object.getAdress());
            statement.setInt(5, object.getId());
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
    public Company readCompanyByName(String name) throws DataBaseException {
        Company result;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery()+" WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            List<Company> allObjects = parseResultSetLite(rs);
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
    public List<Company> getAllCompanyesByParameters(String userId, List<Integer> idList) throws DataBaseException{
        return realiseQuery(getParametrisedReadQuery(userId, idList));
    }

    private String getParametrisedReadQuery(String userId, List<Integer> idList){
        String result = getReadAllQuery()+" WHERE 1=1";
        if(userId!=null){
            result+=" AND user_id = "+userId;
        }
        if(idList!=null&&idList.size()>0){
            result+=" AND id IN (";
            for(Integer id: idList){
                result+=id+",";
            }
            result.substring(0, result.length()-1);
            result+=")";
        }
        return result;
    }

    @Override
    public List<Company> getAllCompanyesWithoutTasks() throws DataBaseException {
        return realiseQuery(getCompanyessWithoutTasksQuery());
    }

    private String getCompanyessWithoutTasksQuery(){
        return getReadAllQuery()+" LEFT JOIN task ON company.id=task.subject_id WHERE subject_id is NULL";
    }

    private String getCompanyesWithOverdueTasksQuery(){
        return getReadAllQuery()+" LEFT JOIN task ON company.id=task.subject_id WHERE task.due_date < NOW()";
    }
}
