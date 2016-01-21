package com.becomejavasenior.impl;


import com.becomejavasenior.Event;
import com.becomejavasenior.OperationType;
import com.becomejavasenior.User;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author PeterKramar
 */

public class EventTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Event> {

    private org.springframework.context.ApplicationContext context;
    private UserTemplateDAOImpl userDAO;

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
        context = new ClassPathXmlApplicationContext("spring-datasource.xml");
        userDAO = (UserTemplateDAOImpl) context.getBean("userDAO");
        String sql = "SELECT * FROM event ORDER BY event_date DESC LIMIT 5";
        List<Event> events = new ArrayList<Event>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            Event event = new Event();
            event.setId((Integer) row.get("id"));
            User user = userDAO.read((Integer) row.get("user_id"));
            event.setUser(user);
            event.setEventDate((Date) row.get("event_date"));
            event.setOperationType(OperationType.valueOf((String) row.get("operation_type")));
            event.setEventContent((String) row.get("content"));
            events.add(event);
        }
        return events;
    }
}
