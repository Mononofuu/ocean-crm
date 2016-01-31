package com.becomejavasenior.impl;


import com.becomejavasenior.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;

/**
 * @author PeterKramar
 */

public class EventTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Event> {

    private ApplicationContext context;
    private GenericTemplateDAO<User> userDAO;

    /*
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("userDAO")
    private GenericTemplateDAO<User> userDAO;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    */

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
        String sql = "SELECT * FROM event ORDER BY event_date";
        return getEvents(sql);
    }

    public List<Event> readLastEvents(int number) {
        String sql = "SELECT * FROM event ORDER BY event_date DESC LIMIT " + number;
        return getEvents(sql);
    }

    private List<Event> getEvents(String sql){
        context = new ClassPathXmlApplicationContext("spring-datasource.xml");
        userDAO = (UserTemplateDAOImpl)context.getBean("userDAO");
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
