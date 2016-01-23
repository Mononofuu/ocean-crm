package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DaoFactory;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.TaskType;
import com.becomejavasenior.interfacedao.TaskTypeDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TaskTypeDAOImpl extends AbstractJDBCDao<TaskType> implements TaskTypeDAO {

    public TaskTypeDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM task_type";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO task_type (name) " +
                "VALUES (?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE task_type SET name = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM task_type WHERE id = ?";
    }

    @Override
    protected List<TaskType> parseResultSet(ResultSet rs) throws DataBaseException {
        List<TaskType> result = new ArrayList<>();
        try {
            while (rs.next()) {
                TaskType taskType = TaskType.valueOf(rs.getString("name"));
                result.add(taskType);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, TaskType object) throws DataBaseException {
        try {
            statement.setString(1, object.name());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, TaskType object) throws DataBaseException {
        try {
            statement.setString(1, object.name());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
