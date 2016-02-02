package com.becomejavasenior.impl;

import com.becomejavasenior.interfacedao.DashboardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 27.01.2016.
 */
@Repository
public class DashboardTemplateDAOImpl extends JdbcDaoSupport implements DashboardDAO {

    @Autowired
    private DataSource myDataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(myDataSource);
    }

    public List<Map<String, Object>> readDasboardInformation() {

        String sql = "SELECT 'allDeals' AS parameter, COUNT(*) AS total FROM deal " +
                "UNION SELECT 'dealsBudget', SUM (budget) FROM deal " +
                "UNION SELECT 'dealWithTasks',COUNT(*) FROM deal WHERE id IN (SELECT subject_id FROM task GROUP BY subject_id) " +
                "UNION SELECT 'dealsWithoutTasks', COUNT(*) FROM deal WHERE NOT id IN (SELECT subject_id FROM task GROUP BY subject_id) " +
                "UNION SELECT 'successDeals', COUNT(*) FROM deal WHERE status_id=5 " +
                "UNION SELECT 'unsuccessClosedDeals',COUNT(*) FROM deal WHERE status_id=6 " +
                "UNION SELECT 'tasksInProgress', COUNT(*) FROM task WHERE is_closed=0 AND is_deleted=0 " +
                "UNION SELECT 'finishedTasks', COUNT(*) FROM task WHERE is_closed=1 " +
                "UNION SELECT 'overdueTasks', COUNT(*) FROM task WHERE NOT (due_date IS NULL) AND due_date < ? AND is_closed = 0 AND is_deleted=0 " +
                "UNION SELECT 'contacts', COUNT(*) FROM contact " +
                "UNION SELECT 'companies', COUNT(*) FROM company";

        Date date = new Date(System.currentTimeMillis());
        List<Map<String, Object>> results = getJdbcTemplate().queryForList(sql, new Date[]{date});

        return results;

    }
}
