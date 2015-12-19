package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.FilterDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class FilterDAOImpl extends AbstractJDBCDao<Filter> implements FilterDAO {
    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM filter";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO filter (user_id, type, date_from, date_to, status_id, manager_id, tasks, tags) VALUES (?,?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE filter SET user_id = ?, type = ?, date_from = ?, date_to = ?, status_id = ?, manager_id = ?, tasks = ?, tags = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM filter WHERE id = ?";
    }

    @Override
    protected List<Filter> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Filter> result = new ArrayList<>();
        try {
            GenericDao statusDao = getDaoFromCurrentFactory(DealStatus.class);
            GenericDao contactDao = getDaoFromCurrentFactory(Contact.class);
            GenericDao userDao = getDaoFromCurrentFactory(User.class);

            while (rs.next()) {
                Filter filter = new Filter();
                filter.setId(rs.getInt("id"));

                User user = (User) userDao.read(rs.getInt("user_id"));
                filter.setUser(user);

                FilterPeriod type = FilterPeriod.valueOf(rs.getString("type"));
                filter.setType(type);

                Timestamp dateFrom = rs.getTimestamp("date_from");
                filter.setDate_from(dateFrom);
                Timestamp dateTo = rs.getTimestamp("date_to");
                filter.setDate_to(dateTo);

                DealStatus status = (DealStatus) statusDao.read(rs.getInt("status_id"));
                filter.setStatus(status);

                Contact manager = (Contact) contactDao.read(rs.getInt("manager_id"));
                filter.setManager(manager);

                FilterTaskType taskType = FilterTaskType.valueOf(rs.getString("tasks"));
                filter.setTaskType(taskType);

                String tags = rs.getString("tags");
                filter.setTags(tags);

                result.add(filter);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }


    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Filter object) throws DataBaseException {
        try {
            statement.setInt(1, object.getUser().getId());
            statement.setString(2, object.getType().name());
            statement.setTimestamp(3, object.getDate_from());
            statement.setTimestamp(4, object.getDate_to());
            statement.setInt(5, object.getStatus().getId());
            if (object.getManager() == null) {
                statement.setNull(6, Types.INTEGER);
            } else {
                statement.setInt(6, object.getManager().getId());
            }
            statement.setString(7, object.getTaskType().name());
            statement.setString(8, object.getTags());

        } catch (SQLException e) {
            throw new DataBaseException();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Filter object) throws DataBaseException {
        try {
            statement.setInt(1, object.getUser().getId());
            statement.setString(2, object.getType().name());
            statement.setTimestamp(3, object.getDate_from());
            statement.setTimestamp(4, object.getDate_to());
            statement.setInt(5, object.getStatus().getId());
            statement.setInt(6, object.getManager().getId());
            statement.setString(7, object.getTaskType().name());
            statement.setString(8, object.getTags());
            statement.setInt(9, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException();
        }
    }
}
