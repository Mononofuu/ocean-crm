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
            GenericDao userDao = getDaoFromCurrentFactory(User.class);
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                Subject subject = getSubject(rs.getInt("subject_id"));
                task.setSubject(subject);
                User user = (User) userDao.read(rs.getInt("user_id"));
                task.setUser(user);
                task.setDateCreated(rs.getTimestamp("created_date"));
                task.setDueTime(rs.getTimestamp("due_date"));
                task.setType(TaskType.values()[rs.getInt("task_type_id")-1]);
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
            statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime()));
            statement.setTimestamp(3, new Timestamp(object.getDueTime().getTime()));
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
            statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime()));
            statement.setTimestamp(3, new Timestamp(object.getDueTime().getTime()));
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

    private Subject getSubject(int id)throws DataBaseException{
        GenericDao<Contact> contactDao = getDaoFromCurrentFactory(Contact.class);
        GenericDao<Company> companyDao = getDaoFromCurrentFactory(Company.class);
        GenericDao<Deal> dealDao = getDaoFromCurrentFactory(Deal.class);
        Contact contact = contactDao.read(id);
        if(contact!=null){
            return contact;
        }
        Company company = companyDao.read(id);
        if(company!=null){
            return company;
        }
        Deal deal = dealDao.read(id);
        if(deal!=null){
            return deal;
        }
        return null;
    }
}
