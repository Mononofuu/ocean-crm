package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Peter on 18.12.2015.
 */
public class DealServiceImpl implements com.becomejavasenior.DealService {

    static final Logger logger = LogManager.getRootLogger();
    DaoFactory daoFactory;
    DealDAO dealDao;
    DealStatusDAO dealStatusDao;

    public DealServiceImpl() throws DataBaseException{
        daoFactory = new PostgreSqlDaoFactory();
        dealDao = (DealDAO)daoFactory.getDao(Deal.class);
        dealStatusDao = (DealStatusDAO) daoFactory.getDao(DealStatus.class);
    }

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
        Deal deal = (Deal) dealDao.read(id);
        return deal;
    }

    @Override
    public List<Deal> findDeals() throws DataBaseException{
        List<Deal> dealList = dealDao.readAll();
        return dealList;
    }

    @Override
    public List<Deal> findDealsLite() throws DataBaseException{
        List<Deal> dealList = dealDao.readAllLite();
        return dealList;
    }

    @Override
    public List<Deal> findDealsByStatus(int statusId) throws DataBaseException {
        List<Deal> dealList = dealDao.readStatusFilter(Integer.valueOf(statusId));
        return dealList;
    }

    @Override
    public List<Deal> findDealsByUser(int userId) throws DataBaseException {
        List<Deal> dealList = dealDao.readUserFilter(Integer.valueOf(userId));
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
            dealsList.retainAll(listIterator.next());
/*
            dealsList = dealsList.stream()
                    .filter(deal ->
                            (listIterator.next().stream().map(Deal::getId).collect(Collectors.toList())).contains(deal.getId()))
                    .collect(Collectors.toList());
*/
        }
        return dealsList;

    }

    @Override
    public List<Deal> findDealsByConditions(int condition) throws DataBaseException {
        List<Deal> dealList = dealDao.readAllWithConditions(condition);
        return dealList;
    }

    @Override
    public List<Deal> findDealsByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        List<Deal> dealList = dealDao.readAllByCreatedDateInterval(dateBegin, dateEnd);
        return dealList;
    }

    @Override
    public List<Deal> findDealsByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        List<Deal> dealList = dealDao.readAllByTasksDueDateInterval(dateBegin, dateEnd);
        return dealList;
    }

    @Override
    public List<DealStatus> getAllDealStatuses() throws DataBaseException {
        return dealStatusDao.readAll();
    }

    @Override
    public DealStatus findDealStatus(int id) throws DataBaseException {
        return dealStatusDao.read(id);
    }
}
