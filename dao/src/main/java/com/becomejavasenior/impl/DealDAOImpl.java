package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealDAOImpl extends AbstractJDBCDao<Deal> implements DealDAO {
    public DealDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO deal(status_id, currency_id, budget, contact_main_id, company_id, data_close) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id= ?;";
    }

    @Override
    protected List<Deal> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Deal> result = new ArrayList<>();
        try{
            while (rs.next()){
                Deal deal = new Deal();
                deal.setId(rs.getInt("id"));
                deal.setName(rs.getString("name"));
                /*
                deal.setStatus(rs.getString("login"));
                deal.setCurrency(rs.getString("password"));
                deal.setBudget(rs.getInt(("budget"));
                deal.setMainContact(rs.getString("email"));
                deal.setDealCompany(rs.getString("phone_mob"));
                deal.setDateWhenDealClose(rs.getDate("data_close"));
                result.add(deal);
                */
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Deal object) throws DataBaseException {

    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM users";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Deal object) throws DataBaseException {

        try {
            statement.setString(1, object.getName());
//            statement.setString(2, object.getLogin());
//            statement.setString(3, object.getPassword());
//            statement.setString(4, object.getEmail());
//            statement.setString(5, object.getPhoneHome());
//            statement.setString(6, object.getPhoneWork());
//            statement.setString(7, object.getLanguage().toString());
//            statement.setString(8, "simply comment");
//            statement.setString(8, object.get"simply comment");
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

}
