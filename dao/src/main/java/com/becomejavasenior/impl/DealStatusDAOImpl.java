package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealStatusDAOImpl extends AbstractJDBCDao<DealStatus> implements DealStatusDAO {
    public DealStatusDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO status_type (name) " +
                "VALUES (?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM status_type WHERE id= ?;";
    }

    @Override
    protected List<DealStatus> parseResultSet(ResultSet rs) throws DataBaseException {
        List<DealStatus> result = new ArrayList<>();
        try{
            while (rs.next()){
                DealStatus dealStatus= new DealStatus();
                dealStatus.setId(rs.getInt("id"));
                dealStatus.setName(rs.getString("name"));
                result.add(dealStatus);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM status_type";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, DealStatus object) throws DataBaseException {

        try {
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, DealStatus object) throws DataBaseException {
    }
}
