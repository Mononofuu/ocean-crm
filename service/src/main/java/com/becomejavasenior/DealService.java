package com.becomejavasenior;

import java.sql.Date;
import java.util.List;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;


/**
 * Created by Peter on 18.12.2015.
 */
public interface DealService{

        public static final int CONDITION_DEALS_OPENED = 1;
        public static final int CONDITION_DEALS_SUCCESS = 2;
        public static final int CONDITION_DEALS_CLOSED_AND_NOT_IMPLEMENTED = 3;
        public static final int CONDITION_DEALS_WITHOUT_TASKS = 4;
        public static final int CONDITION_DEALS_WITH_EXPIRED_TASKS = 5;
        public static final int CONDITION_DEALS_DELETED = 6;

        Deal findDealById(int id) throws DataBaseException;
        Deal saveDeal(Deal deal) throws DataBaseException;
        void deleteDeal(int id) throws DataBaseException;
        List<Deal> findDeals() throws DataBaseException;
        List<Deal> findDealsLite() throws DataBaseException;
        List<Deal> findDealsByStatus(int statusId) throws DataBaseException;
        List<Deal> findDealsByUser(int statusId) throws DataBaseException;
        List<Deal> findDealsByTags(String tag) throws DataBaseException;
        List<Deal> findDealsByConditions(int condition) throws DataBaseException;
        List<Deal> findDealsByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException;
        List<Deal> findDealsByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException;
        List<Deal> findDealsByFilters(List<String> listOfFilters) throws DataBaseException;
        List<DealStatus> getAllDealStatuses() throws DataBaseException;
        DealStatus findDealStatus(int id) throws DataBaseException;
}
