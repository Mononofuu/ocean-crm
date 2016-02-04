package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import com.becomejavasenior.interfacedao.FilterDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Repository
public class FilterDAOImpl extends AbstractJDBCDao<Filter> implements FilterDAO {
    private static final Logger LOGGER = LogManager.getLogger(FilterDAO.class);
    @Autowired
    public UserDAO userDAO;
    @Autowired
    public DealStatusDAO dealStatusDAO;
    @Autowired
    public ContactDAO contactDAO;

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM filter";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO filter (name, user_id, type, date_from, date_to, status_id, manager_id, tasks, tags) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE filter SET name = ?, user_id = ?, type = ?, date_from = ?, date_to = ?, status_id = ?, manager_id = ?, tasks = ?, tags = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM filter WHERE id = ?";
    }

    @Override
    protected List<Filter> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Filter> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Filter filter = new Filter();
                filter.setId(rs.getInt("id"));

                filter.setName(rs.getString("name"));

                User user = userDAO.read(rs.getInt("user_id"));
                filter.setUser(user);

                FilterPeriod type = FilterPeriod.valueOf(rs.getString("type"));
                filter.setType(type);

                Timestamp dateFrom = rs.getTimestamp("date_from");
                filter.setDate_from(dateFrom);
                Timestamp dateTo = rs.getTimestamp("date_to");
                filter.setDate_to(dateTo);

                DealStatus status = dealStatusDAO.read(rs.getInt("status_id"));
                filter.setStatus(status);

                Contact manager = contactDAO.read(rs.getInt("manager_id"));
                filter.setManager(manager);

                FilterTaskType taskType = FilterTaskType.valueOf(rs.getString("tasks"));
                filter.setTaskType(taskType);

                String tags = rs.getString("tags");
                filter.setTags(tags);

                result.add(filter);
            }
        } catch (SQLException e) {
            LOGGER.catching(e);
            throw new DataBaseException(e);
        }
        return result;
    }


    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Filter object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getUser().getId());
            statement.setString(3, object.getType().name());
            statement.setTimestamp(4, object.getDate_from());
            statement.setTimestamp(5, object.getDate_to());
            statement.setInt(6, object.getStatus().getId());
            if (object.getManager() == null) {
                statement.setNull(7, Types.INTEGER);
            } else {
                statement.setInt(7, object.getManager().getId());
            }
            statement.setString(8, object.getTaskType().name());
            statement.setString(9, object.getTags());

        } catch (SQLException e) {
            LOGGER.catching(e);
            throw new DataBaseException();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Filter object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getUser().getId());
            statement.setString(3, object.getType().name());
            statement.setTimestamp(4, object.getDate_from());
            statement.setTimestamp(5, object.getDate_to());
            statement.setInt(6, object.getStatus().getId());
            if(object.getManager()!=null){
                statement.setInt(7, object.getManager().getId());
            }else {
                statement.setNull(7, Types.INTEGER);
            }
            statement.setString(8, object.getTaskType().name());
            statement.setString(9, object.getTags());
            statement.setInt(10, object.getId());
        } catch (SQLException e) {
            LOGGER.catching(e);
            throw new DataBaseException();
        }
    }
}
