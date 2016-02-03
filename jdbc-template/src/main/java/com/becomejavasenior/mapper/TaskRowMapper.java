package com.becomejavasenior.mapper;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */

@Component
public class TaskRowMapper implements RowMapper<Task> {
    private final static Logger LOGGER = LogManager.getLogger(TaskRowMapper.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private DealDAO dealDAO;
    
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        try {
            Subject subject = getSubject(resultSet.getInt("subject_id"));
            User user = userDAO.read(resultSet.getInt("user_id"));
            task.setSubject(subject);
            task.setUser(user);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        task.setDateCreated(resultSet.getTimestamp("created_date"));
        task.setDueTime(resultSet.getTimestamp("due_date"));
        task.setType(TaskType.values()[resultSet.getInt("task_type_id")-1]);
        task.setComment(resultSet.getString("comment"));
        task.setIsClosed(resultSet.getByte("is_closed"));
        task.setIsDeleted(resultSet.getByte("is_deleted"));
        return task;
    }

    private Subject getSubject(int id)throws DataBaseException {
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
