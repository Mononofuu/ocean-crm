package com.becomejavasenior.impl;

import com.becomejavasenior.Task;
import com.becomejavasenior.GenericTemplateDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Date;
import java.util.List;

/**
 * @author PeterKramar
 */

public class TaskTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Task> {

    public void create(Task object) {

    }

    public Task read(int key) {
        return null;
    }

    public void update(Task object) {

    }

    public void delete(int id) {

    }

    public List<Task> readAll() {
        return null;
    }

    public int findTotalTasksInProgress(){
        String sql = "SELECT COUNT(*) FROM task WHERE is_closed=0 AND is_deleted=0";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalFinishedTasks(){
        String sql = "SELECT COUNT(*) FROM task WHERE is_closed=1";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalOverdueTasks(){
        Date date = new Date(System.currentTimeMillis());
        String sql = "SELECT COUNT(*) FROM task WHERE NOT (due_date IS null) AND due_date < ? AND is_closed = 0 AND is_deleted=0 ";

        int total = getJdbcTemplate().queryForObject(
                sql, new Date[] {date} , Integer.class);
        return total;
    }

}
