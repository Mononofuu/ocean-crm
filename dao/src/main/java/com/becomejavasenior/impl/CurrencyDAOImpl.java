package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.Currency;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CurrencyDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class CurrencyDAOImpl extends AbstractJDBCDao<Currency> implements CurrencyDAO {
    public CurrencyDAOImpl(Connection connection) throws DataBaseException {
        super(connection);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM currency";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO currency (code, name) " +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE currency SET code = ?, name = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM currency WHERE id= ?";
    }

    @Override
    protected List<Currency> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Currency> result = new ArrayList<>();
        try {
            while (rs.next()) {
            Currency currency = new Currency();
            currency.setId(rs.getInt("id"));
            currency.setCode(rs.getString("code"));
            currency.setName(rs.getString("name"));
            result.add(currency);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Currency object) throws DataBaseException {
        try {
            statement.setString(1, object.getCode());
            statement.setString(2, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Currency object) throws DataBaseException {
        try {
            statement.setString(1, object.getCode());
            statement.setString(2, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
