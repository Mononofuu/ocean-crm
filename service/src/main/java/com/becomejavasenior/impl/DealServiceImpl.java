package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealContactDAO;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import com.becomejavasenior.interfacedao.FilterDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Peter on 18.12.2015.
 */
public class DealServiceImpl implements com.becomejavasenior.DealService {

    static final Logger logger = LogManager.getRootLogger();
    DaoFactory daoFactory;
    DealDAO dealDao;
    DealStatusDAO dealStatusDao;
    DealContactDAO dealContactDAO;
    FilterDAO filterDAO;

    public DealServiceImpl() {
        try {
            daoFactory = new PostgreSqlDaoFactory();
            dealDao = (DealDAO) daoFactory.getDao(Deal.class);
            dealStatusDao = (DealStatusDAO) daoFactory.getDao(DealStatus.class);
            dealContactDAO = (DealContactDAO) daoFactory.getDao(DealContact.class);
            filterDAO = (FilterDAO) daoFactory.getDao(Filter.class);
        } catch (DataBaseException e) {
            logger.catching(e);
        }
    }

    @Override
    public Deal saveDeal(Deal deal) throws DataBaseException {
        if(deal.getId() == 0){
            return dealDao.create(deal);
        }else{
            dealDao.update(deal);
            return dealDao.read(deal.getId());
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
        List<Deal> dealList = dealDao.readTagFilter("'" + tag + "')))");
        return dealList;
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

    @Override
    public void deleteDealStatus(int id) throws DataBaseException {
        dealStatusDao.delete(id);
    }

    @Override
    public DealStatus saveDealStatus(DealStatus dealStatus) throws DataBaseException {
        if (dealStatus.getId() == 0) {
            return dealStatusDao.create(dealStatus);
        } else {
            dealStatusDao.update(dealStatus);
            return dealStatusDao.read(dealStatus.getId());
        }
    }

    @Override
    public void addContactToDeal(Deal createdDeal, Contact contact) throws DataBaseException {
        DealContact dealContact = new DealContact();
        dealContact.setDeal(createdDeal);
        dealContact.setContact(contact);
        dealContactDAO.create(dealContact);
    }

    @Override
    public Filter saveDealFilter(Filter filter) throws DataBaseException {
        if (filter.getId() == 0) {
            return filterDAO.create(filter);
        } else {
            filterDAO.update(filter);
            return filterDAO.read(filter.getId());
        }
    }

    @Override
    public List<Filter> findDealFilters() throws DataBaseException {
        return filterDAO.readAll();
    }

    @Override
    public Filter findDealFilterById(int id) throws DataBaseException {
        return filterDAO.read(id);
    }

    @Override
    public void deleteDealFilter(int id) throws DataBaseException {
        filterDAO.delete(id);
    }

    public List<Tag> getAllDealTags() throws DataBaseException {
        TagDAO tagDAO = (TagDAO)daoFactory.getDao(Tag.class);
        return tagDAO.readAll(SubjectType.CONTACT_TAG);
    }

    @Override
    public List<Deal> findDealsByFilters(List<String> listOfFilters) throws DataBaseException {

        List<Deal> dealsList = new ArrayList<>();
        String filter;
        for(int i=0;i<listOfFilters.size();i++){
            filter = listOfFilters.get(i);
            if("task_NO_TASKS".equals(filter)){
                listOfFilters.set(i,"when_notask");
            }else if("task_EXPIRED".equals(filter)){
                listOfFilters.set(i,"when_expired");
            }else if(filter.startsWith("when") || filter.startsWith("task")){
                if(!"when_PERIOD".equals(filter)){
                    listOfFilters.set(i,setFilterPeriod(filter));
                }
            }
        }
        dealsList = dealDao.readAllWithConditions(listOfFilters);
        return dealsList;
    }

    public String setFilterPeriod(String filter){
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateBegin = new java.sql.Date(cal.getTime().getTime());
        Date dateEnd = dateBegin;
        switch (filter) {
            case "when_TODAY":
                filter = "when";
                break;
            case "when_FOR_THREE_DAYS":
                cal.add(Calendar.DATE, -2);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                filter = "when";
                break;
            case "when_WEEK":
                cal.add(Calendar.DATE, -6);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                filter = "when";
                break;
            case "when_MONTH":
                cal.add(Calendar.MONTH, -1);
                cal.add(Calendar.DATE, 1);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                filter = "when";
                break;
            case "when_QUARTER":
                cal.add(Calendar.MONTH, -3);
                cal.add(Calendar.DATE, 1);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                filter = "when";
                break;
            case "task_TODAY":
                filter = "task";
                break;
            case "task_TOMORROW":
                cal.add(Calendar.DATE,1);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                dateEnd = dateBegin;
                filter = "task";
                break;
            case "task_THIS_WEEK":
                /*
                int day = cal.get(Calendar.DAY_OF_WEEK);
                int correction = 0;
                switch(day){
                    case 1:
                        correction = 6;
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        correction = day-2;
                        break;
                    default:
                        break;
                }
                cal.add(Calendar.DATE,-correction);
                */
                cal.set(Calendar.DAY_OF_WEEK,2);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                cal.add(Calendar.DATE,6);
                dateEnd = new java.sql.Date(cal.getTime().getTime());
                filter = "task";
                break;
            case "task_THIS_MONTHS":
                cal.set(Calendar.DAY_OF_MONTH, 1);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                cal.add(Calendar.MONTH,1);
                cal.add(Calendar.DATE,-1);
                dateEnd = new java.sql.Date(cal.getTime().getTime());
                filter = "task";
                break;
            case "task_THIS_QUARTER":
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH)%3);
                dateBegin = new java.sql.Date(cal.getTime().getTime());
                cal.add(Calendar.MONTH,3);
                cal.add(Calendar.DATE,-1);
                dateEnd = new java.sql.Date(cal.getTime().getTime());
                filter = "task";
                break;
            default:
                break;
        }
        return filter + "_" + formatter.format(dateBegin) +  "_" + formatter.format(dateEnd);
    }


}

