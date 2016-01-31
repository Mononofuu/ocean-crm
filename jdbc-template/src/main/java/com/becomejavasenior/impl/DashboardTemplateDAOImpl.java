package com.becomejavasenior.impl;

import com.becomejavasenior.Event;
import com.becomejavasenior.interfacedao.DashboardDAO;
import com.becomejavasenior.mapper.EventRowMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 27.01.2016.
 */
public class DashboardTemplateDAOImpl extends JdbcDaoSupport implements DashboardDAO{

    public List<Map<String, Object>> readDasboardInformation() {

        String sql =  "SELECT 'allDeals' as parameter, COUNT(*) as total FROM deal " +
                "UNION SELECT 'dealsBudget', SUM (budget) FROM deal " +
                "UNION SELECT 'dealWithTasks',COUNT(*) FROM deal WHERE id IN (SELECT subject_id FROM task GROUP BY subject_id) " +
                "UNION SELECT 'dealsWithoutTasks', COUNT(*) FROM deal WHERE NOT id IN (SELECT subject_id FROM task GROUP BY subject_id) " +
                "UNION SELECT 'successDeals', COUNT(*) FROM deal WHERE status_id=5 " +
                "UNION SELECT 'unsuccessClosedDeals',COUNT(*) FROM deal WHERE status_id=6 " +
                "UNION SELECT 'tasksInProgress', COUNT(*) FROM task WHERE is_closed=0 AND is_deleted=0 " +
                "UNION SELECT 'finishedTasks', COUNT(*) FROM task WHERE is_closed=1 " +
                "UNION SELECT 'overdueTasks', COUNT(*) FROM task WHERE NOT (due_date IS null) AND due_date < ? AND is_closed = 0 AND is_deleted=0 " +
                "UNION SELECT 'contacts', COUNT(*) FROM contact " +
                "UNION SELECT 'companies', COUNT(*) FROM company";

        Date date = new Date(System.currentTimeMillis());
        List<Map<String,Object>> results = getJdbcTemplate().queryForList(sql, new Date[] {date});

        return results;

    }
}
