package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.EventDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
public class EventDAOImpl extends AbstractJDBCDao<Event> implements EventDAO {
    public EventDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM event";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO event (user_id, event_date, operation_type, content) VALUES (?, ?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE event SET user_id = ?, event_date = ?, operation_type = ?, content = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM event WHERE id = ?";
    }

    @Override
    protected List<Event> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Event> result = new ArrayList<>();
        try {
            GenericDao<User> userDao = getDaoFromCurrentFactory(User.class);
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                User user = userDao.read(rs.getInt("user_id"));
                event.setUser(user);
                event.setEventDate(rs.getTimestamp("event_date"));
                event.setOperationType(OperationType.valueOf(rs.getString("operation_type")));
                event.setEventContent(rs.getString("content"));
                result.add(event);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Event object) throws DataBaseException {
        try {
            statement.setInt(1, object.getUser().getId());
            statement.setTimestamp(2, new Timestamp(object.getEventDate().getTime()));
            statement.setString(3, object.getOperationType().name());
            statement.setString(4, object.getEventContent());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Event object) throws DataBaseException {
        try {
            statement.setInt(1, object.getUser().getId());
            statement.setTimestamp(2, new Timestamp(object.getEventDate().getTime()));
            statement.setString(3, object.getOperationType().name());
            statement.setString(4, object.getEventContent());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
