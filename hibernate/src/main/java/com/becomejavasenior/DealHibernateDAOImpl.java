package com.becomejavasenior;

import com.becomejavasenior.interfacedao.DealDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */
@Repository
public class DealHibernateDAOImpl extends AbstractHibernateDAO<Deal> implements DealDAO{

    @Override
    public Class getObjectСlass() {
        return Deal.class;
    }

    @Override
    public List<Deal> readStatusFilter(int statusId) throws DataBaseException {
        return null;
    }

    @Override
    public List<Deal> readUserFilter(int userId) throws DataBaseException {
        return null;
    }

    @Override
    public List<Deal> readTagFilter(String tag) throws DataBaseException {
        return null;
    }

    @Override
    public List<Deal> readAllWithConditions(DealFilters condition) throws DataBaseException {
        return null;
    }

    @Override
    public List<Deal> readAllByCreatedDateInterval(java.sql.Date dateBegin, java.sql.Date dateEnd) throws DataBaseException {
        return null;
    }

    @Override
    public List<Deal> readAllByTasksDueDateInterval(java.sql.Date dateBegin, java.sql.Date dateEnd) throws DataBaseException {
        return null;
    }

    @Override
    public List<Deal> readAllWithConditions(List<String> conditionsList) throws DataBaseException {
        return null;
    }

    @Override
    public int findTotalDeals() {
        return 0;
    }

    @Override
    public int findTotalDealsBudget() {
        return 0;
    }

    @Override
    public int findTotalDealsWithTasks() {
        return 0;
    }

    @Override
    public int findTotalDealsWithoutTasks() {
        return 0;
    }

    @Override
    public int findTotalSuccessDeals() {
        return 0;
    }

    @Override
    public int findTotalUnsuccessClosedDeals() {
        return 0;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Deal deal = new Deal();
        deal.setId(id);
        delete(deal);
    }

}
