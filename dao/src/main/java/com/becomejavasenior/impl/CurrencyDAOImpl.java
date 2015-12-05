package com.becomejavasenior.impl;

<<<<<<< HEAD
import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import com.becomejavasenior.interfacedao.UserDAO;
=======
import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.Currency;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CurrencyDAO;
>>>>>>> master

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class CurrencyDAOImpl extends AbstractJDBCDao<Currency> implements CurrencyDAO {
    public CurrencyDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
=======
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
>>>>>>> master
    }

    @Override
    public String getCreateQuery() {
<<<<<<< HEAD
        return "INSERT INTO currency (code, name) " +
                "VALUES (?, ?);";
=======
        return "INSERT INTO currency (code, name) VALUES (?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE currency SET code = ?, name = ? WHERE id = ?";
>>>>>>> master
    }

    @Override
    public String getDeleteQuery() {
<<<<<<< HEAD
        return "DELETE FROM currency WHERE id= ?;";
=======
        return "DELETE FROM currency WHERE id= ?";
>>>>>>> master
    }

    @Override
    protected List<Currency> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Currency> result = new ArrayList<>();
<<<<<<< HEAD
        try{
            while (rs.next()){
=======
        try {
            while (rs.next()) {
>>>>>>> master
                Currency currency = new Currency();
                currency.setId(rs.getInt("id"));
                currency.setCode(rs.getString("code"));
                currency.setName(rs.getString("name"));
<<<<<<< HEAD
=======

>>>>>>> master
                result.add(currency);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM currency";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Currency object) throws DataBaseException {

=======
    protected void prepareStatementForInsert(PreparedStatement statement, Currency object) throws DataBaseException {
>>>>>>> master
        try {
            statement.setString(1, object.getCode());
            statement.setString(2, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Currency object) throws DataBaseException {
=======
    protected void prepareStatementForUpdate(PreparedStatement statement, Currency object) throws DataBaseException {
        try {
            statement.setString(1, object.getCode());
            statement.setString(2, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
>>>>>>> master
    }
}
