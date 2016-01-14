package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DaoFactory;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.PhoneType;
import com.becomejavasenior.interfacedao.PhoneTypeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneTypeDAOImpl extends AbstractJDBCDao<PhoneType> implements PhoneTypeDAO {

    public PhoneTypeDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM phone_type";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM phone_tag WHERE id = ?;";
    }

    @Override
    protected List<PhoneType> parseResultSet(ResultSet rs) throws DataBaseException {
        List<PhoneType> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PhoneType phoneType = PhoneType.valueOf(rs.getString("name"));
                result.add(phoneType);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO phone_type (name) VALUES (?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, PhoneType object) throws DataBaseException {
        try {
            statement.setString(1, object.name());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE phone_type SET name = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, PhoneType object) throws DataBaseException {
        try {
            statement.setString(1, object.name());
            statement.setInt(2, object.ordinal() + 1);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}