package com.becomejavasenior.impl;


import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author PeterKramar
 */
@Repository
public class EventTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Event> {
    private final static Logger LOGGER = LogManager.getLogger(EventTemplateDAOImpl.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DataSource myDataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(myDataSource);
    }

    public void create(Event object) {

    }

    public Event read(int key) {
        return null;
    }

    public void update(Event object) {

    }

    public void delete(int id) {

    }

    public int findTotalEvents(){
        String sql = "SELECT COUNT(*) FROM event";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
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
        List<Event> events = new ArrayList<Event>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            Event event = new Event();
            event.setId((Integer) row.get("id"));
            User user = null;
            try {
                user = userDAO.read((Integer) row.get("user_id"));
            } catch (DataBaseException e) {
                LOGGER.error(e);
            }
            event.setUser(user);
            event.setEventDate((Date) row.get("event_date"));
            event.setOperationType(OperationType.valueOf((String) row.get("operation_type")));
            event.setEventContent((String) row.get("content"));
            events.add(event);
        }
        return events;
    }

}
