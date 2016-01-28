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

    private ApplicationContext context;
    private DashboardTemplateDAOImpl dashboardDAO;

    public Map<String, Object> readDasboardInformation() {

        context = new ClassPathXmlApplicationContext("spring-datasource.xml");
        dashboardDAO = (DashboardTemplateDAOImpl) context.getBean("dashboardDAO");
        Map<String, Object> result = new HashMap<String, Object>();

        JdbcTemplate template = dashboardDAO.getJdbcTemplate();

        /*
        String sql = "SELECT COUNT(*) FROM deal";
        int total = template.queryForObject(sql, Integer.class);
        result.put("allDeals", total);

        sql = "SELECT SUM (budget) FROM deal";
        total = template.queryForObject(sql, Integer.class);
        result.put("dealsBudget", total);

        sql = "SELECT COUNT(*) FROM deal WHERE id IN (SELECT subject_id FROM task GROUP BY subject_id)";
        total = template.queryForObject(sql, Integer.class);
        result.put("dealWithTasks", total);

        sql = "SELECT COUNT(*) FROM deal WHERE NOT id IN (SELECT subject_id FROM task GROUP BY subject_id)";
        total = template.queryForObject(sql, Integer.class);
        result.put("dealsWithoutTasks", total);

        sql = "SELECT COUNT(*) FROM deal WHERE status_id=5";
        total = template.queryForObject(sql, Integer.class);
        result.put("successDeals", total);

        sql = "SELECT COUNT(*) FROM deal WHERE status_id=6";
        total = template.queryForObject(sql, Integer.class);
        result.put("unsuccessClosedDeals", total);

        sql = "SELECT COUNT(*) FROM task WHERE is_closed=0 AND is_deleted=0";
        total = template.queryForObject(sql, Integer.class);
        result.put("tasksInProgress", total);

        sql = "SELECT COUNT(*) FROM task WHERE is_closed=1";
        total = template.queryForObject(sql, Integer.class);
        result.put("finishedTasks", total);

        Date date = new Date(System.currentTimeMillis());
        sql = "SELECT COUNT(*) FROM task WHERE NOT (due_date IS null) AND due_date < ? AND is_closed = 0 AND is_deleted=0 ";
        total = template.queryForObject(sql, new Date[] {date} , Integer.class);
        result.put("overdueTasks", total);

        sql = "SELECT COUNT(*) FROM contact";
        total = template.queryForObject(sql, Integer.class);
        result.put("contacts", total);

        sql = "SELECT COUNT(*) FROM company";
        total = template.queryForObject(sql, Integer.class);
        result.put("companies", total);
*/

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
        List<Map<String,Object>> results = template.queryForList(sql, new Date[] {date});
        for (Map<String,Object> m : results){
            result.put( (String) m.get("parameter"), m.get("total"));
        }

        sql = "SELECT event.id id,login,event_date,operation_type,content FROM event, users where event.user_id = users.id ORDER BY event_date DESC LIMIT 5";
        List<Event> events  = template.query(sql, new EventRowMapper());
        result.put("events", events);

        return result;

    }
}
