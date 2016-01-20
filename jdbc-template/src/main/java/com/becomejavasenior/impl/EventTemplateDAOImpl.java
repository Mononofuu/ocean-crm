package com.becomejavasenior.impl;


import com.becomejavasenior.Event;
import com.becomejavasenior.OperationType;
import com.becomejavasenior.User;
import com.becomejavasenior.config.ApplicationContext;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author PeterKramar
 */

public class EventTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Event> {

    public void create(Event object) {

    }

    public Event read(int key) {
        return null;
    }

    public void update(Event object) {

    }

    public void delete(int id) {

    }

    public List<Event> readAll() {
        return null;
    }

    public int findTotalEvents(){
        String sql = "SELECT COUNT(*) FROM event";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public List<Event> readLastEvents() {
        String sql = "SELECT * FROM event ORDER BY event_date DESC LIMIT 5";
        List<Event> events = new ArrayList<Event>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            Event event = new Event();
            event.setId((Integer) row.get("id"));
//            UserTemplateDAOImpl userDAO = new UserTemplateDAOImpl();
//            User user = userDAO.read((Integer) row.get("user_date"));
//            event.setUser(user);
            event.setEventDate((Date) row.get("event_date"));
            event.setOperationType(OperationType.valueOf((String) row.get("operation_type")));
            event.setEventContent((String) row.get("content"));
            events.add(event);
        }
        return events;
    }

}
