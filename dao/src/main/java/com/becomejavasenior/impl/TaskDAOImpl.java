package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.TaskDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class TaskDAOImpl extends AbstractJDBCDao<Task> implements TaskDAO {
    public TaskDAOImpl(  Connection connection) throws DataBaseException {
        super(connection);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM task";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO task (subject_id, created_date, due_date, user_id, task_type_id, comment) " +
                "VALUES (?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {

        return "UPDATE task SET subject_id = ?, created_date = ?, due_date = ?, user_id = ?, task_type_id = ?, comment = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM task WHERE id = ?";
    }

    @Override
    protected List<Task> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Task> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
                Subject subject = (Subject) subjectDao.read(rs.getInt("subject_id"));
                task.setSubject(subject);
                GenericDao userDao = getDaoFromCurrentFactory(User.class);
                User user = (User) userDao.read(rs.getInt("user_id"));
                task.setUser(user);
                task.setDateCreated(rs.getDate("created_date"));
                task.setDueTime(rs.getDate("due_date"));
                task.setType(TaskType.values()[rs.getInt("task_type_id")]);
                task.setComment(rs.getString("comment"));
                result.add(task);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Task object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime())); //TODO ?
            statement.setTimestamp(3, new Timestamp(object.getDueTime().getTime())); //TODO ?
            statement.setInt(4, object.getUser().getId());
            statement.setInt(5, object.getType().ordinal() + 1);
            statement.setString(6, object.getComment());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Task object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime())); //TODO ?
            statement.setTimestamp(3, new Timestamp(object.getDueTime().getTime())); //TODO ?
            statement.setInt(4, object.getUser().getId());
            statement.setInt(5, object.getType().ordinal() + 1);
            statement.setString(6, object.getComment());
            statement.setInt(7, object.getId());

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Task> getAllTasksBySubjectId(int id) throws DataBaseException {
        List<Task> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + " WHERE subject_id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }
}
