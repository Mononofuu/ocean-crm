package com.becomejavasenior.interfaceservice;

import java.util.List;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;


/**
 * Created by Peter on 18.12.2015.
 */
public interface DealService{

        Deal findDealById(int id) throws DataBaseException;
        void saveDeal(Deal deal) throws DataBaseException;
        void deleteDeal(int id) throws DataBaseException;
        List<Deal> findDeals() throws DataBaseException;
        List<Deal> findDealsLite() throws DataBaseException;
        List<Deal> findDealsByStatus(int statusId) throws DataBaseException;
        List<Deal> findDealsByUser(int statusId) throws DataBaseException;
        List<Deal> findDealsByTags(String tag) throws DataBaseException;
        List<Deal> findDealsByFilters(List<List<Deal>> listsOfDeals) throws DataBaseException;

}
