package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class TaskDAOImpl extends AbstractJDBCDao<Task> implements TaskDAO {
    private Logger logger = LogManager.getLogger(TaskDAOImpl.class);

    @Override
    public List<Task> getAllTasksByParameters(Map<String, String[]> parameters) throws DataBaseException{
        List<Task> result;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ){
            ResultSet rs = statement.executeQuery(getParametrisedReadQuery(parameters));
            result = parseResultSet(rs);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e);
        }
        if (result == null) {
            throw new DataBaseException();
        }
        return result;
    }

    private String getParametrisedReadQuery(Map<String, String[]> parameters){
        String result = "SELECT * FROM task";
        // фильтрация по предуставновленным фильтрам
        String filter = parameters.get("filtername")[0];
        switch (filter){
            case "mytasks":
                result += " WHERE user_id = " + parameters.get("currentuser")[0];
                break;
            case "overduetasks":
                result += " WHERE due_date < NOW() AND user_id = " + parameters.get("currentuser")[0];
                break;
            default:
                result += " WHERE 1=1";
                break;
        }
        // добавление фильтра по дате
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            String date = parameters.get("duedate")[0];
            if(!"".equals(date)){
                String time = parameters.get("duetime")[0];
                if("".equals(time)){
                    time = "23:59";
                }
                Date dueDate = dateFormat.parse(date+" "+time);
                result += " AND due_date <= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(dueDate)+"'";
            }
        } catch (ParseException e) {
            logger.error("Неверный формат даты", e);
        }
        // добавление фильтра по типу задачи
        String taskType = parameters.get("tasktype")[0];
        if(!"".equals(taskType)){
            result += " AND task_type_id = "+taskType;
        }
        // добавление филтра по пользователю
        String userId = parameters.get("user")[0];
        if (!"".equals(userId)){
            result += " AND user_id = "+userId;
        }
        return result;
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
