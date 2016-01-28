package com.becomejavasenior.mapper;

import com.becomejavasenior.Event;
import com.becomejavasenior.OperationType;
import com.becomejavasenior.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {

    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        User user = new User();
        user.setLogin(resultSet.getString("login"));
        event.setUser(user);
        event.setEventDate(resultSet.getDate("event_date"));
        event.setOperationType(OperationType.valueOf((String) resultSet.getString("operation_type")));
        event.setEventContent(resultSet.getString("content"));
        return event;
    }
}
