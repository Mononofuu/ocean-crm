package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class TaskTemplateDAOImpl extends JdbcDaoSupport implements TaskDAO {
    private final static Logger LOGGER = LogManager.getLogger(TaskTemplateDAOImpl.class);
    private static final String INSERT_QUERY="INSERT INTO task (subject_id, created_date, due_date, user_id, task_type_id," +
            " comment, is_closed, is_deleted) " +
            "VALUES (?,?,?,?,?,?,?,?)";
    private static final String READ_ALL = "SELECT * FROM task";
    private static final String UPDATE_QUERY = "UPDATE task SET subject_id = :subject_id, created_date = :created_date, " +
            "due_date = :due_date, user_id = :user_id, task_type_id = :task_type_id, comment = :comment, " +
            "is_closed = :is_closed, is_deleted = :is_deleted WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM task WHERE id = ";
    @Autowired
    private RowMapper<Task> taskRowMapper;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private DealDAO dealDAO;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public Task create(Task object) throws DataBaseException {
        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                try {
                    statement.setInt(1, object.getSubject().getId());
                    statement.setTimestamp(2, new Timestamp(object.getDateCreated().getTime()));
                    java.util.Date dueTime = object.getDueTime();
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
                    LOGGER.error(e);
                }
                return statement;
            }
        }, holder);
        return read((Integer)holder.getKeys().get("id"));
    }

    @Override
    public Task read(int key) throws DataBaseException {
        return getJdbcTemplate().queryForObject(READ_ALL+" WHERE id = ?", new Object[]{key}, taskRowMapper);
    }

    @Override
    public Task readLite(int key) throws DataBaseException {
        return getJdbcTemplate().queryForObject(READ_ALL+" WHERE id = ?", new Object[]{key}, taskRowMapper);
    }

    @Override
    public void update(Task task) throws DataBaseException {
        Map<String, Object> params = new HashMap<>();
        params.put("subject_id", task.getSubject().getId());
        params.put("created_date", new Timestamp(task.getDateCreated().getTime()));
        Date dueTime = task.getDueTime();
        if(dueTime == null){
            params.put("due_date", null);
        }else{
            params.put("due_date", new Timestamp(task.getDueTime().getTime()));
        }
        params.put("user_id", task.getUser().getId());
        params.put("task_type_id", task.getType().ordinal()+1);
        params.put("comment", task.getComment());
        params.put("is_closed", task.getIsClosed());
        params.put("is_deleted", task.getIsDeleted());
        params.put("id", task.getId());
        namedParameterJdbcTemplate.update(UPDATE_QUERY,params);
    }

    @Override
    public void delete(int id) throws DataBaseException {
        getJdbcTemplate().update(DELETE_QUERY+id);
    }

    @Override
    public List<Task> readAll() throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL, taskRowMapper);
    }

    @Override
    public List<Task> readAllLite() throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL, taskRowMapper);
    }

    @Override
    public List<Task> getAllTasksBySubjectId(int id) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL+" WHERE subject_id = " + id, taskRowMapper);
    }

    @Override
    public List<Task> getAllTasksByParameters(String userId, java.util.Date date, String taskTypeId) throws DataBaseException {
        return getJdbcTemplate().query(getParametrisedReadQuery(userId, date, taskTypeId), taskRowMapper);
    }

    @Override
    public List<Task> getAllTasksBySubject(Subject subject) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL+" WHERE subject_id = "+subject.getId(), taskRowMapper);
    }

    private String getParametrisedReadQuery(String userId, Date date, String taskTypeId){
        String result = READ_ALL+" WHERE 1=1";
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
    public Subject getSubject(int id) throws DataBaseException {
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
}
