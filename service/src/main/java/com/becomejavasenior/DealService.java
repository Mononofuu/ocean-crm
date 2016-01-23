package com.becomejavasenior;

import java.sql.Date;
import java.util.List;


/**
 * Created by Peter on 18.12.2015.
 */
public interface DealService {

    int CONDITION_DEALS_OPENED = 1;
    int CONDITION_DEALS_SUCCESS = 2;
    int CONDITION_DEALS_CLOSED_AND_NOT_IMPLEMENTED = 3;
    int CONDITION_DEALS_WITHOUT_TASKS = 4;
    int CONDITION_DEALS_WITH_EXPIRED_TASKS = 5;
    int CONDITION_DEALS_DELETED = 6;

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

    void deleteDealStatus(int id) throws DataBaseException;

    DealStatus saveDealStatus(DealStatus dealStatus) throws DataBaseException;

    void addContactToDeal(Deal deal, Contact contact) throws DataBaseException;

    Filter saveDealFilter(Filter filter) throws DataBaseException;

    List<Filter> findDealFilters() throws DataBaseException;

    Filter findDealFilterById(int id) throws DataBaseException;

    void deleteDealFilter(int id) throws DataBaseException;
}
