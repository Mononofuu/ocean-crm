package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealDAOImpl extends AbstractJDBCDao<Deal> implements DealDAO {
    public DealDAOImpl(Connection connection) throws DataBaseException {
        super(connection);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM deal";
>>>>>>> master
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO deal(id, status_id, currency_id, budget, contact_main_id, company_id, data_close) " +
                "VALUES(?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE deal SET status_id = ?, currency_id = ?, budget = ?, contact_main_id = ?," +
                "company_id = ?, data_close = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
<<<<<<< HEAD
        return "DELETE FROM deal WHERE id= ?;";
=======
        return "DELETE FROM deal WHERE id = ?";
>>>>>>> master
    }

    @Override
    protected List<Deal> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Deal> result = new ArrayList<>();
<<<<<<< HEAD
        GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
        GenericDao dealStatusDao = getDaoFromCurrentFactory(DealStatus.class);
        GenericDao currencyDao = getDaoFromCurrentFactory(Currency.class);
        GenericDao contactDao = getDaoFromCurrentFactory(Contact.class);
        GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
        try{
            while (rs.next()){
                Deal deal = new Deal();
                int id = rs.getInt("id");
                // —читываем данные из таблицы subject
                Subject subject = (Subject) subjectDao.read(id);
                deal.setId(id);
                deal.setName(subject.getName());
                DealStatus dealStatus = (DealStatus) dealStatusDao.read(rs.getInt("status_id"));
                deal.setStatus(dealStatus);
                Currency currency = (Currency) currencyDao.read(rs.getInt("currency_id"));
                deal.setCurrency(currency);
                deal.setBudget(rs.getInt("budget"));
                Contact contact = (Contact) contactDao.read(rs.getInt("contact_main_id"));
                deal.setMainContact(contact);
                Company company = (Company) companyDao.read(rs.getInt("company_id"));
                deal.setDealCompany(company);
                deal.setDateWhenDealClose(rs.getDate("data_close"));
=======
        try {
            while (rs.next()) {
                Deal deal = new Deal();
                int id = rs.getInt("id");

                GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
                Subject subject = (Subject) subjectDao.read(id);
                deal.setId(id);
                deal.setName(subject.getName());
                deal.setBudget(rs.getInt("budget"));
                deal.setDateWhenDealClose(rs.getTimestamp("data_close"));

                GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
                Company company = (Company) companyDao.read(rs.getInt("company_id"));
                deal.setDealCompany(company);

                GenericDao contactDao = getDaoFromCurrentFactory(Contact.class);
                Contact contact = (Contact) contactDao.read(rs.getInt("contact_main_id"));
                deal.setMainContact(contact);

                GenericDao dealStatusDao = getDaoFromCurrentFactory(DealStatus.class);
                DealStatus dealStatus = (DealStatus) dealStatusDao.read(rs.getInt("status_id"));
                deal.setStatus(dealStatus);

                GenericDao currencyDao = getDaoFromCurrentFactory(Currency.class);
                Currency currency = (Currency) currencyDao.read(rs.getInt("currency_id"));
                deal.setCurrency(currency);

                SubjectTagDAOImpl subjectTagDAOImpl = (SubjectTagDAOImpl) getDaoFromCurrentFactory(SubjectTag.class);
                deal.setTags(subjectTagDAOImpl.getAllTagsBySubjectId(id));

                DealContactDAOImpl dealContactDAOImpl = (DealContactDAOImpl) getDaoFromCurrentFactory(DealContact.class);
                deal.setContacts(dealContactDAOImpl.getAllContactsBySubjectId(id));

                FileDAOImpl fileDao = (FileDAOImpl) getDaoFromCurrentFactory(File.class);
                deal.setFiles(fileDao.getAllFilesBySubjectId(id));

                CommentDAOImpl commentDAO = (CommentDAOImpl) getDaoFromCurrentFactory(Comment.class);
                deal.setComments(commentDAO.getAllCommentsBySubjectId(id));

                TaskDAOImpl taskDAO = (TaskDAOImpl) getDaoFromCurrentFactory(Task.class);
                deal.setTasks(taskDAO.getAllTasksBySubjectId(id));

>>>>>>> master
                result.add(deal);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
<<<<<<< HEAD
    protected void prepareStatementForUpdate(PreparedStatement statement, Deal object) throws DataBaseException {
        try{
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
=======
    protected void prepareStatementForInsert(PreparedStatement statement, Deal object) throws DataBaseException {
        try {
>>>>>>> master
            statement.setInt(1, createSubject(object));
            statement.setInt(2, object.getStatus().getId());
            statement.setInt(3, object.getCurrency().getId());
            statement.setInt(4, object.getBudget());
            statement.setInt(5, object.getMainContact().getId());
            statement.setInt(6, object.getDealCompany().getId());
<<<<<<< HEAD
            statement.setDate(7, new Date(object.getDateWhenDealClose().getTime()));
        }catch (SQLException e){
=======
            if (object.getDateWhenDealClose() == null) {
                statement.setNull(7, Types.TIMESTAMP);

            } else {
                statement.setTimestamp(7, new Timestamp(object.getDateWhenDealClose().getTime()));
            }
        } catch (SQLException e) {
>>>>>>> master
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Deal object) throws DataBaseException {
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
            statement.setInt(1, object.getStatus().getId());
            statement.setInt(2, object.getCurrency().getId());
            statement.setInt(3, object.getBudget());
            statement.setInt(4, object.getMainContact().getId());
            statement.setInt(5, object.getDealCompany().getId());
            statement.setTimestamp(6, new Timestamp(object.getDateWhenDealClose().getTime()));
            statement.setInt(7, object.getId());
    public String getReadAllQuery() {
        return "SELECT * FROM deal";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Deal object) throws DataBaseException {

        try {
            statement.setInt(1, createSubject(object));
            statement.setInt(2, object.getStatus().getId());
            statement.setInt(3, object.getCurrency().getId());
            statement.setInt(4, object.getBudget());
            statement.setInt(5, object.getMainContact().getId());
            statement.setInt(6, object.getDealCompany().getId());
            statement.setDate(7, new Date(object.getDateWhenDealClose().getTime()));
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE deal SET status_id = ?, currency_id = ?, budget = ?, contact_main_id = ?, company_id = ?, data_close = ? WHERE id = ?;";
    }

}
