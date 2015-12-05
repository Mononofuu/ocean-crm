package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealStatus;
import com.becomejavasenior.interfacedao.DealStatusDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class DealStatusDAOImpl extends AbstractJDBCDao<DealStatus> implements DealStatusDAO {
    public DealStatusDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
=======
/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealStatusDAOImpl extends AbstractJDBCDao<DealStatus> implements DealStatusDAO {
    public DealStatusDAOImpl(Connection connection) throws DataBaseException {
        super(connection);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM status_type";
    }

    @Override
    public String getCreateQuery() {
<<<<<<< HEAD
        return "INSERT INTO status_type (name) " +
                "VALUES (?);";
=======
        return "INSERT INTO status_type (name) VALUES (?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE status_type SET name = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
<<<<<<< HEAD
        return "DELETE FROM status_type WHERE id= ?;";
=======
        return "DELETE FROM status_type WHERE id= ?";
>>>>>>> master
    }

    @Override
    protected List<DealStatus> parseResultSet(ResultSet rs) throws DataBaseException {
        List<DealStatus> result = new ArrayList<>();
<<<<<<< HEAD
        try{
            while (rs.next()){
                DealStatus dealStatus= new DealStatus();
                dealStatus.setId(rs.getInt("id"));
                dealStatus.setName(rs.getString("name"));
                result.add(dealStatus);
=======
        try {
            while (rs.next()) {
                DealStatus status = new DealStatus();
                status.setId(rs.getInt("id"));
                status.setName(rs.getString("name"));

                result.add(status);
>>>>>>> master
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
=======
    protected void prepareStatementForInsert(PreparedStatement statement, DealStatus object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, DealStatus object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public int checkIfExists(DealStatus status) throws DataBaseException {
        String query = "SELECT * FROM status_type WHERE name = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, status.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public DealStatus create(DealStatus object) throws DataBaseException {
        int checkedId = checkIfExists(object);
        if (checkedId < 0) {
            return super.create(object);
        } else {
            return read(checkedId);
        }
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, DealStatus object) throws DataBaseException {
    }
}
