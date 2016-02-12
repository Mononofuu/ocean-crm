package com.becomejavasenior;

import com.becomejavasenior.interfacedao.DealDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */
@Repository
public class DealHibernateDAOImpl extends AbstractHibernateDAO<Deal> implements DealDAO{

    private final static Logger LOGGER = LogManager.getLogger(DealHibernateDAOImpl.class);
    private static final String DEAL_SELECT_TAG = " WHERE deal.id IN(SELECT subject_id FROM subject_tag " +
            "WHERE subject_tag.tag_id IN (SELECT id FROM tag WHERE name IN (";
    private static final String DEAL_SELECT_STATUS_ID = " where status_id = :statusId";
    private static final String DEAL_SELECT_OPENED = " WHERE deal.data_close IS null";
    private static final String DEAL_SELECT_BY_USER = " WHERE responsible_id = :responsibleId";
    private static final String DEAL_SELECT_WITHOUT_TASKS = " WHERE NOT deal.id IN (SELECT subject_id FROM task GROUP BY subject_id)";
    private static final String DEAL_SELECT_WITH_EXPIRED_TASKS = " WHERE deal.id IN (SELECT subject_id FROM task WHERE NOT (due_date IS null) AND due_date < :dueDate GROUP BY subject_id)";
    private static final String DEAL_SELECT_SUCCESS = " WHERE deal.status_id=5";
    private static final String DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED = " WHERE deal.status_id=6";
    private static final String DEAL_SELECT_DELETED = " WHERE deal.status_id=7";
    private static final String DEAL_SELECT_PERIOD_CREATED_DATE = " WHERE DATE(deal.created_date) BETWEEN :dateBegin AND :dateEnd";
    private static final String DEAL_SELECT_TASK_DUE_DATE_INTERVAL = "WHERE deal.id IN (SELECT subject_id FROM task WHERE DATE(due_date) BETWEEN :dateBegin AND :dateEnd GROUP BY subject_id)";
    private static final String READ_ALL_QUERY = "SELECT deal.id, status_id, currency_id, budget, contact_main_id, company_id, data_close, deal.created_date, name, responsible_id, content_owner_id, removed FROM deal JOIN subject ON subject.id=deal.id ";

    @Override
    public Class getObjectСlass() {
        return Deal.class;
    }

    @Override
    public List<Deal> readStatusFilter(int statusId) throws DataBaseException {
        Query query = getCurrentSession().createSQLQuery(
                READ_ALL_QUERY+DEAL_SELECT_STATUS_ID)
                .addEntity(Deal.class)
                .setParameter("statusId", statusId);
        return query.list();
    }

    @Override
    public List<Deal> readUserFilter(int userId) throws DataBaseException {
        Query query = getCurrentSession().createSQLQuery(
                READ_ALL_QUERY+DEAL_SELECT_BY_USER)
                .addEntity(Deal.class)
                .setParameter("responsibleId", userId);
        return query.list();
    }

    @Override
    public List<Deal> readTagFilter(String tag) throws DataBaseException {
        Query query = getCurrentSession().createSQLQuery(
                READ_ALL_QUERY+DEAL_SELECT_TAG + tag + ")")
                .addEntity(Deal.class);
        return query.list();
    }

    @Override
    public List<Deal> readAllWithConditions(DealFilters condition) throws DataBaseException {
        List<Deal> result = new ArrayList<>();
        String sql;
        switch (condition){
            case OPENED:
                sql = READ_ALL_QUERY + DEAL_SELECT_OPENED;
                break;
            case SUCCESS:
                sql = READ_ALL_QUERY + DEAL_SELECT_SUCCESS;
                break;
            case CLOSED:
                sql = READ_ALL_QUERY + DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED;
                break;
            case WITHOUT_TASKS:
                sql = READ_ALL_QUERY + DEAL_SELECT_WITHOUT_TASKS;
                break;
            case WITH_EXPIRED_TASKS:
                sql = READ_ALL_QUERY + DEAL_SELECT_WITH_EXPIRED_TASKS;
                break;
            case DELETED:
                sql = READ_ALL_QUERY + DEAL_SELECT_DELETED;
                break;
            default:
                return result;
        }
        Query query = getCurrentSession().createSQLQuery(
                sql)
                .addEntity(Deal.class);
        if(condition == DealFilters.WITH_EXPIRED_TASKS){
            query.setParameter("dueDate", new Date(System.currentTimeMillis()));
        }
        return query.list();
    }

    @Override
    public List<Deal> readAllByCreatedDateInterval(java.sql.Date dateBegin, java.sql.Date dateEnd) throws DataBaseException {
        Query query = getCurrentSession().createSQLQuery(
                READ_ALL_QUERY + DEAL_SELECT_PERIOD_CREATED_DATE)
                .addEntity(Deal.class)
                .setParameter("dateBegin", dateBegin)
                .setParameter("dateEnd", dateEnd);
        return query.list();
    }

    @Override
    public List<Deal> readAllByTasksDueDateInterval(java.sql.Date dateBegin, java.sql.Date dateEnd) throws DataBaseException {
        Query query = getCurrentSession().createSQLQuery(
                READ_ALL_QUERY + DEAL_SELECT_TASK_DUE_DATE_INTERVAL)
                .addEntity(Deal.class)
                .setParameter("dateBegin", dateBegin)
                .setParameter("dateEnd", dateEnd);
        return query.list();
    }

    @Override
    public List<Deal> readAllWithConditions(List<String> conditionsList) throws DataBaseException {

        String sql = createSQL(conditionsList);
        Query query = getCurrentSession().createSQLQuery(sql)
            .addEntity(Deal.class);

            for(String condition: conditionsList) {
                if (condition.startsWith("selectedfilter_my")) {
                    query.setParameter("responsibleId", Integer.parseInt(condition.replace("selectedfilter_my_", "")));
                } else if ("selectedfilter_expired".equals(condition)) {
                    query.setParameter("dueDate", new Date(System.currentTimeMillis()));
                } else if (condition.startsWith("phase")) {
                    query.setParameter("statusId", Integer.parseInt(condition.replace("phase_", "")));
                } else if (condition.startsWith("manager")) {
                    query.setParameter("responsibleId", Integer.parseInt(condition.replace("manager_", "")));
                } else if (condition.startsWith("when") || condition.startsWith("task")) {
                    SimpleDateFormat formatter = new SimpleDateFormat();
                    formatter.applyPattern("MM/dd/yyyy");
                    String dateFrom = condition.substring(5, 15);
                    String dateTo = condition.substring(16, 26);
                    try {
                        Date date = formatter.parse(dateFrom);
                        query.setParameter("dateBegin", date);
                        date = formatter.parse(dateTo);
                        query.setParameter("dateBegin", date);
                    }
                    catch (ParseException e) {
                        LOGGER.error(e);
                    }
                }
            }
        return query.list();
    }

    @Override
    public int findTotalDeals() {
        return (Integer) getCurrentSession().createSQLQuery(
                "SELECT COUNT(*) FROM deal").
                uniqueResult();
    }

    @Override
    public int findTotalDealsBudget() {
        return (Integer) getCurrentSession().createSQLQuery(
                "SELECT SUM (budget) FROM deal").
                uniqueResult();
    }

    @Override
    public int findTotalDealsWithTasks() {
        return (Integer) getCurrentSession().createSQLQuery(
                "SELECT COUNT(*) FROM deal WHERE id IN (SELECT subject_id FROM task GROUP BY subject_id)").
                uniqueResult();
    }

    @Override
    public int findTotalDealsWithoutTasks() {
        return (Integer) getCurrentSession().createSQLQuery(
                "SELECT COUNT(*) FROM deal WHERE NOT id IN (SELECT subject_id FROM task GROUP BY subject_id)").
                uniqueResult();
    }

    @Override
    public int findTotalSuccessDeals() {
        return (Integer) getCurrentSession().createSQLQuery(
                "SELECT COUNT(*) FROM deal WHERE status_id=5").
                uniqueResult();
    }

    @Override
    public int findTotalUnsuccessClosedDeals() {
        return (Integer) getCurrentSession().createSQLQuery(
                "SELECT COUNT(*) FROM deal WHERE status_id=6").
                uniqueResult();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Deal deal = new Deal();
        deal.setId(id);
        delete(deal);
    }

    private String createSQL(List<String> conditionsList){

        StringBuilder whereCondition = new StringBuilder();

        for(String condition: conditionsList){
            if(condition.startsWith("selectedfilter")){
                appendSelectedFilterSQL(whereCondition, condition);
            }else if(condition.startsWith("when")) {
                whereCondition.append(changeCondition(DEAL_SELECT_PERIOD_CREATED_DATE));
            }else if(condition.startsWith("phase")) {
                whereCondition.append(changeCondition(DEAL_SELECT_STATUS_ID));
            }else if(condition.startsWith("manager")) {
                whereCondition.append(changeCondition(DEAL_SELECT_BY_USER));
            }else if(condition.startsWith("tags")) {
                String tagString = condition.replace("tags_", "");
                whereCondition.append(changeCondition(DEAL_SELECT_TAG + "'" +
                        tagString + "')))"));
            }else if(condition.startsWith("task")) {
                whereCondition.append(changeCondition(DEAL_SELECT_TASK_DUE_DATE_INTERVAL));
            }
        }

        String sql;
        sql = READ_ALL_QUERY + whereCondition.toString().replaceFirst("AND", "WHERE");

        return sql;
    }

    private StringBuilder appendSelectedFilterSQL(StringBuilder whereCondition, String condition) {
        String cond = condition;
        if(condition.startsWith("selectedfilter_my")){
            cond = "selectedfilter_my";
        }
        switch (cond){
            case "selectedfilter_open":
                whereCondition.append(changeCondition(DEAL_SELECT_OPENED));
                break;
            case "selectedfilter_success":
                whereCondition.append(changeCondition(DEAL_SELECT_SUCCESS));
                break;
            case "selectedfilter_fail":
                whereCondition.append(changeCondition(DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED));
                break;
            case "selectedfilter_notask":
                whereCondition.append(changeCondition(DEAL_SELECT_WITHOUT_TASKS));
                break;
            case "selectedfilter_expired":
                whereCondition.append(changeCondition(DEAL_SELECT_WITH_EXPIRED_TASKS));
                break;
            case "selectedfilter_deleted":
                whereCondition.append(changeCondition(DEAL_SELECT_DELETED));
                break;
            case "selectedfilter_my":
                whereCondition.append(changeCondition(DEAL_SELECT_BY_USER));
                break;
            default:
                break;
        }
        return whereCondition;
    }

    private String changeCondition(String condition){
        return condition.replaceFirst("WHERE","AND");
    }

}
