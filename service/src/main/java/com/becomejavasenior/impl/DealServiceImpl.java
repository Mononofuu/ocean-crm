package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfaceservice.DealService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Peter on 18.12.2015.
 */
public class DealServiceImpl implements DealService{

    static final Logger logger = LogManager.getRootLogger();
    DealDAOImpl dealDao = new DealDAOImpl();

    @Override
    public void saveDeal(Deal deal) throws DataBaseException {
        if(deal.getId() == 0){
            dealDao.create(deal);
        }else{
            dealDao.update(deal);
        }
    }

    @Override
    public void deleteDeal(int id) throws DataBaseException {
        dealDao.delete(id);
    }

    @Override
    public Deal findDealById(int id) throws DataBaseException {
        Deal deal = dealDao.read(id);
        return deal;
    }

    @Override
    public List<Deal> findDeals() throws DataBaseException{
        List<Deal> dealList = dealDao.readAll();
        return dealList;
    }

    @Override
    public List<Deal> findDealsByStatus(int statusId) throws DataBaseException {
        List<Deal> dealList = dealDao.readStatusFilter(Integer.valueOf(statusId));
        return dealList;
    }

    @Override
    public List<Deal> findDealsByUser(int userId) throws DataBaseException {
        List<Deal> dealList = dealDao.readStatusFilter(Integer.valueOf(userId));
        return dealList;
    }

    @Override
    public List<Deal> findDealsByTags(String tag) throws DataBaseException {
        List<Deal> dealList = dealDao.readTagFilter("'" + tag + "')))");;
        return dealList;
    }

    @Override
    public List<Deal> findDealsByFilters(List<List<Deal>> list) throws DataBaseException{
        Iterator<List<Deal>> listIterator = list.iterator();
        List<Deal> dealsList = listIterator.next();
        while (dealsList.size() != 0 && listIterator.hasNext()) {
            //                   dealsListTemp = listIterator.next();
            dealsList = dealsList.stream()
                    .filter(deal ->
//                                (dealsListTemp.stream().map(Deal::getId).collect(Collectors.toList())).contains(deal.getId()))
                            (listIterator.next().stream().map(Deal::getId).collect(Collectors.toList())).contains(deal.getId()))
                    .collect(Collectors.toList());
        }
        return dealsList;
    }

}
