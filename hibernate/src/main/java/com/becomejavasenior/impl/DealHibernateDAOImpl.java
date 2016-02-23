package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
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
//    private static final String DEAL_SELECT_STATUS_ID = " where status_id = :statusId";
//    private static final String DEAL_SELECT_OPENED = " WHERE deal.data_close IS null";
//    private static final String DEAL_SELECT_BY_USER = " WHERE responsible_id = :responsibleId";
//    private static final String DEAL_SELECT_WITHOUT_TASKS = " WHERE NOT deal.id IN (SELECT subject_id FROM task GROUP BY subject_id)";
//    private static final String DEAL_SELECT_WITH_EXPIRED_TASKS = " WHERE deal.id IN (SELECT subject_id FROM task WHERE NOT (due_date IS null) AND due_date < :dueDate GROUP BY subject_id)";
//    private static final String DEAL_SELECT_SUCCESS = " WHERE deal.status_id=5";
//    private static final String DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED = " WHERE deal.status_id=6";
//    private static final String DEAL_SELECT_DELETED = " WHERE deal.status_id=7";
//    private static final String DEAL_SELECT_PERIOD_CREATED_DATE = " WHERE DATE(deal.created_date) BETWEEN :dateBegin AND :dateEnd";
//    private static final String DEAL_SELECT_TASK_DUE_DATE_INTERVAL = "WHERE deal.id IN (SELECT subject_id FROM task WHERE DATE(due_date) BETWEEN :dateBegin AND :dateEnd GROUP BY subject_id)";
    private static final String READ_ALL_QUERY = "SELECT deal.id, status_id, currency_id, budget, contact_main_id, company_id, data_close, deal.created_date, name, responsible_id, content_owner_id, removed FROM deal JOIN subject ON subject.id=deal.id ";

    @Override
    public Class getObjectСlass() {
        return Deal.class;
    }

    @Override
    public List<Deal> readStatusFilter(int statusId) throws DataBaseException {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("status.id", statusId));
        return criteria.list();

    }

    @Override
    public List<Deal> readUserFilter(int userId) throws DataBaseException {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("responsible.id", userId));
        return criteria.list();

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
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        switch (condition){
            case OPENED:
                criteria.add(Restrictions.eq("dateWhenDealClose", null));
                break;
            case SUCCESS:
                criteria.add(Restrictions.eq("status.id", 5));
                break;
            case CLOSED:
                criteria.add(Restrictions.eq("status.id", 6));
                break;
            case WITHOUT_TASKS:
                //TODO
//                sql = READ_ALL_QUERY + DEAL_SELECT_WITHOUT_TASKS;
                break;
            case WITH_EXPIRED_TASKS:
                //TODO
//                sql = READ_ALL_QUERY + DEAL_SELECT_WITH_EXPIRED_TASKS;
                break;
            case DELETED:
                criteria.add(Restrictions.eq("status.id", 7));
                break;
            default:
                return new ArrayList<>();
        }
        return criteria.list();
    }

    @Override
    public List<Deal> readAllByCreatedDateInterval(java.sql.Date dateBegin, java.sql.Date dateEnd) throws DataBaseException {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.between("dateCreated", dateBegin, dateEnd));
        return criteria.list();

    }

    @Override
    public List<Deal> readAllByTasksDueDateInterval(java.sql.Date dateBegin, java.sql.Date dateEnd) throws DataBaseException {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.createCriteria("tasks")
                .createCriteria("dueTime")
                .add(Restrictions.between("dueTime", dateBegin, dateEnd));
        return criteria.list();

    }


    @Override
    public int findTotalDeals() {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        return criteria.list().size();

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

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("status.id", 5));
        return criteria.list().size();

    }

    @Override
    public int findTotalUnsuccessClosedDeals() {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("status.id", 6));
        return criteria.list().size();

    }

    @Override
    public List<Tag> readAllDealsTags() throws DataBaseException {
        //TODO
        return null;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Deal deal = new Deal();
        deal.setId(id);
        delete(deal);
    }

    @Override
    public List<Deal> readAllWithConditions(List<String> conditionsList) throws DataBaseException {

        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());

        for(String condition: conditionsList){
            if(condition.startsWith("selectedfilter")){
                createFilter(criteria, condition);
            }else if(condition.startsWith("when")) {
                SimpleDateFormat formatter = new SimpleDateFormat();
                formatter.applyPattern("MM/dd/yyyy");
                String dateFrom = condition.substring(5, 15);
                String dateTo = condition.substring(16, 26);
                try {
                    Date date = formatter.parse(dateFrom);
                    criteria.add(Restrictions.ge("dateCreated", date));
                    date = formatter.parse(dateTo);
                    criteria.add(Restrictions.le("dateCreated", date));
                }
                catch (ParseException e) {
                    LOGGER.error(e);
                }
            }else if(condition.startsWith("phase")) {
                criteria.add(Restrictions.eq("status.id", Integer.parseInt(condition.replace("phase_", ""))));
            }else if(condition.startsWith("manager")) {
                criteria.add(Restrictions.eq("responsible.id", Integer.parseInt(condition.replace("manager_", ""))));
            }else if(condition.startsWith("tags")) {
                //TODO
//                String tagString = condition.replace("tags_", "");
//                whereCondition.append(changeCondition(DEAL_SELECT_TAG + "'" +
//                        tagString + "')))"));
            }else if(condition.startsWith("task")) {
                //TODO
//                whereCondition.append(changeCondition(DEAL_SELECT_TASK_DUE_DATE_INTERVAL));
            }
        }
        return criteria.list();

    }

    private Criteria createFilter(Criteria criteria, String condition) {

        String cond = condition;
        if(condition.startsWith("selectedfilter_my")){
            cond = "selectedfilter_my";
        }
        switch (cond){
            case "selectedfilter_open":
                criteria.add(Restrictions.eq("dateWhenDealClose", null));
                break;
            case "selectedfilter_success":
                criteria.add(Restrictions.eq("status.id", 5));
                break;
            case "selectedfilter_fail":
                criteria.add(Restrictions.eq("status.id", 6));
                break;
            case "selectedfilter_notask":
                //TODO
                criteria.add(Restrictions.eq("tasks", null));
                break;
            case "selectedfilter_expired":
                //TODO
//                criteria.createCriteria("tasks");
//                criteria.createCriteria("dueTime");
//                criteria.add(Restrictions.ne("dueTime", null));
//                criteria.add(Restrictions.le("dueTime", new Date(System.currentTimeMillis())));
//                criteria.createAlias("c.tasks","task");
//                criteria.add(Restrictions.ne("task.dueTime", null));
//                criteria.add(Restrictions.le("task.dueTime", new Date(System.currentTimeMillis())));
                break;
            case "selectedfilter_deleted":
                criteria.add(Restrictions.eq("status.id", 7));
                break;
            case "selectedfilter_my":
                criteria.add(Restrictions.eq("responsible.id", Integer.parseInt(condition.replace("selectedfilter_my_", ""))));
                break;
            default:
                break;
        }
        return criteria;
    }


}
