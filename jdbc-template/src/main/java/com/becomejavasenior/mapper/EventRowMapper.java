package com.becomejavasenior.mapper;

import com.becomejavasenior.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//заготовка
public class EventRowMapper implements RowMapper<Event> {

    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        event.setEventDate(resultSet.getDate("event_date"));
        event.setEventContent(resultSet.getString("content"));
        return event;
    }
}
