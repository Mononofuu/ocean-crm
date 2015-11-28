package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DealDAOImpl extends AbstractJDBCDao<Deal> implements DealDAO {
    public DealDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO deal(id, status_id, currency_id, budget, contact_main_id, company_id, data_close) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM deal WHERE id= ?;";
    }

    @Override
    protected List<Deal> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Deal> result = new ArrayList<>();
        GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
        GenericDao dealStatusDao = getDaoFromCurrentFactory(DealStatus.class);
        GenericDao currencyDao = getDaoFromCurrentFactory(Currency.class);
        GenericDao contactDao = getDaoFromCurrentFactory(Contact.class);
        GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
        try{
            while (rs.next()){
                Deal deal = new Deal();
                int id = rs.getInt("id");
                // Считываем данные из таблицы subject
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
                result.add(deal);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Deal object) throws DataBaseException {
        try{
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
            statement.setInt(1, createSubject(object));
            statement.setInt(2, object.getStatus().getId());
            statement.setInt(3, object.getCurrency().getId());
            statement.setInt(4, object.getBudget());
            statement.setInt(5, object.getMainContact().getId());
            statement.setInt(6, object.getDealCompany().getId());
            statement.setDate(7, new Date(object.getDateWhenDealClose().getTime()));
        }catch (SQLException e){
            throw new DataBaseException(e);
        }
    }

    @Override
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
