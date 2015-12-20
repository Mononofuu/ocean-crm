package com.becomejavasenior.impl;

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

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealStatusDAOImpl extends AbstractJDBCDao<DealStatus> implements DealStatusDAO {

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM status_type";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO status_type (name, color, systemDefault) VALUES (?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE status_type SET name = ?, color = ?, systemDefault = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM status_type WHERE id= ?";
    }

    @Override
    protected List<DealStatus> parseResultSet(ResultSet rs) throws DataBaseException {
        List<DealStatus> result = new ArrayList<>();
        try {
            while (rs.next()) {
                DealStatus status = new DealStatus();
                status.setId(rs.getInt("id"));
                status.setName(rs.getString("name"));
                status.setColor(rs.getString("color"));
                status.setSystemDefault(rs.getBoolean("systemDefault"));

                result.add(status);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, DealStatus object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getColor());
            statement.setBoolean(3, object.isSystemDefault());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, DealStatus object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getColor());
            statement.setBoolean(3, object.isSystemDefault());
            statement.setInt(4, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public int checkIfExists(DealStatus status) throws DataBaseException {
        String query = "SELECT * FROM status_type WHERE name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
    }
}
