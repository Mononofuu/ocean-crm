package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */

public class TaskDAOImpl extends AbstractJDBCDao<Task> implements TaskDAO {
    private static final Logger LOGGER = LogManager.getLogger(TaskDAOImpl.class);
    @Autowired
    public UserDAO userDAO;
    @Autowired
    public CompanyDAO companyDAO;
    @Autowired
    public ContactDAO contactDAO;
    @Autowired
    public DealDAO dealDAO;

    @Override
    public List<Task> getAllTasksByParameters(String userId, Date date, String taskTypeId) throws DataBaseException {
        List<Task> result;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ){
            LOGGER.info(getParametrisedReadQuery(userId, date, taskTypeId));
            ResultSet rs = statement.executeQuery(getParametrisedReadQuery(userId, date, taskTypeId));
            result = parseResultSet(rs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        if (result == null) {
            throw new DataBaseException();
        }
        return result;
    }

    private String getParametrisedReadQuery(String userId, Date date, String taskTypeId){
        String result = getReadAllQuery()+" WHERE 1=1";
        if(userId!=null){
            result+=" AND user_id = "+userId;
        }
        if(date!=null){
            result+=" AND due_date <= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(date)+"'";
        }
        if(taskTypeId!=null){
            result+=" AND task_type_id = "+taskTypeId;
        }
        return result;
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM task";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO task (subject_id, created_date, due_date, user_id, task_type_id, comment, is_closed, is_deleted) " +
                "VALUES (?,?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE task SET subject_id = ?, created_date = ?, due_date = ?, user_id = ?, task_type_id = ?, comment = ?, is_closed = ?, is_deleted = ? WHERE id = ?";
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
                Subject subject = getSubject(rs.getInt("subject_id"));
                task.setSubject(subject);
                User user = userDAO.read(rs.getInt("user_id"));
                task.setUser(user);
                task.setDateCreated(rs.getTimestamp("created_date"));
                task.setDueTime(rs.getTimestamp("due_date"));
                task.setType(TaskType.values()[rs.getInt("task_type_id")-1]);
                task.setComment(rs.getString("comment"));
                task.setIsClosed(rs.getByte("is_closed"));
                task.setIsDeleted(rs.getByte("is_deleted"));
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
            statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime()));
            Date dueTime = object.getDueTime();
            if(dueTime == null){
                statement.setTimestamp(3, null);
            }else{
                statement.setTimestamp(3, new Timestamp(object.getDueTime().getTime()));
            }
            statement.setInt(4, object.getUser().getId());
            statement.setInt(5, object.getType().ordinal() + 1);
            statement.setString(6, object.getComment());
            statement.setByte(7, object.getIsClosed());
            statement.setByte(8, object.getIsDeleted());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Task object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime()));
            Date dueTime = object.getDueTime();
            if(dueTime == null){
                statement.setTimestamp(3, null);
            }else{
                statement.setTimestamp(3, new Timestamp(object.getDueTime().getTime()));
            }
            statement.setInt(4, object.getUser().getId());
            statement.setInt(5, object.getType().ordinal() + 1);
            statement.setString(6, object.getComment());
            statement.setByte(7, object.getIsClosed());
            statement.setByte(8, object.getIsDeleted());
            statement.setInt(9, object.getId());

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Task> getAllTasksBySubjectId(int id) throws DataBaseException {
        List<Task> result;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + " WHERE subject_id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    public Subject getSubject(int id)throws DataBaseException{
        Contact contact = contactDAO.read(id);
        if(contact!=null){
            return contact;
        }
        Company company = companyDAO.read(id);
        if(company!=null){
            return company;
        }
        Deal deal = dealDAO.read(id);
        if(deal!=null){
            return deal;
        }
        return null;
    }

    @Override
    public List<Task> getAllTasksBySubject(Subject subject) throws DataBaseException {
        List<Task> result;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + " WHERE subject_id = ?")) {
            statement.setInt(1, subject.getId());
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs, subject);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    private List<Task> parseResultSet(ResultSet rs, Subject subject) throws DataBaseException {
        List<Task> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setSubject(subject);
                User user = userDAO.read(rs.getInt("user_id"));
                task.setUser(user);
                task.setDateCreated(rs.getTimestamp("created_date"));
                task.setDueTime(rs.getTimestamp("due_date"));
                task.setType(TaskType.values()[rs.getInt("task_type_id")-1]);
                task.setComment(rs.getString("comment"));
                task.setIsClosed(rs.getByte("is_closed"));
                task.setIsDeleted(rs.getByte("is_deleted"));
                result.add(task);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }
}
