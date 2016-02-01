package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;
import com.becomejavasenior.DealFilters;
import com.becomejavasenior.GenericDao;

import java.sql.Date;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealDAO  extends GenericDao<Deal> {


    public List<Deal> readStatusFilter(int statusId) throws DataBaseException;
    public List<Deal> readUserFilter(int userId) throws DataBaseException;
    public List<Deal> readTagFilter(String tag) throws DataBaseException;
    public List<Deal> readAllWithConditions(DealFilters condition) throws DataBaseException;
    public List<Deal> readAllByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException;
    public List<Deal> readAllByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException;
    public List<Deal> readAllWithConditions(List<String> conditionsList) throws DataBaseException;
    public int findTotalDeals();
    public int findTotalDealsBudget();
    public int findTotalDealsWithTasks();
    public int findTotalDealsWithoutTasks();
    public int findTotalSuccessDeals();
    public int findTotalUnsuccessClosedDeals();

}
