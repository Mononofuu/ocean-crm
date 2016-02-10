package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DealServiceImpl implements com.becomejavasenior.DealService {

    static final Logger logger = LogManager.getRootLogger();

    @Autowired
    DealDAO dealDAO;
    @Autowired
    DealStatusDAO dealStatusDAO;
    @Autowired
    DealContactDAO dealContactDAO;
    @Autowired
    FilterDAO filterDAO;
    @Autowired
    TagDAO tagDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public Deal saveDeal(Deal deal) throws DataBaseException {
        if(deal.getId() == 0){
            return dealDAO.create(deal);
        }else{
            dealDAO.update(deal);
            return dealDAO.read(deal.getId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public void deleteDeal(int id) throws DataBaseException {
        dealDAO.delete(id);
    }

    @Override
    public Deal findDealById(int id) throws DataBaseException {
        return dealDAO.read(id);
    }

    @Override
    public List<Deal> findDeals() throws DataBaseException{
        return dealDAO.readAll();
    }

    @Override
    public List<Deal> findDealsLite() throws DataBaseException{
        return dealDAO.readAllLite();
    }

    @Override
    public List<Deal> findDealsByStatus(int statusId) throws DataBaseException {
        return dealDAO.readStatusFilter(statusId);
    }

    @Override
    public List<Deal> findDealsByUser(int userId) throws DataBaseException {
        return dealDAO.readUserFilter(userId);
    }

    @Override
    public List<Deal> findDealsByTags(String tag) throws DataBaseException {
        return dealDAO.readTagFilter("'" + tag + "')))");
    }

    @Override
    public List<Deal> findDealsByConditions(DealFilters condition) throws DataBaseException {
        return dealDAO.readAllWithConditions(condition);
    }

    @Override
    public List<Deal> findDealsByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        return dealDAO.readAllByCreatedDateInterval(dateBegin, dateEnd);
    }

    @Override
    public List<Deal> findDealsByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        return dealDAO.readAllByTasksDueDateInterval(dateBegin, dateEnd);
    }


    @Override
    public List<DealStatus> getAllDealStatuses() throws DataBaseException {
        return dealStatusDAO.readAll();
    }

    @Override
    public DealStatus findDealStatus(int id) throws DataBaseException {
        return dealStatusDAO.read(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public void deleteDealStatus(int id) throws DataBaseException {
        dealStatusDAO.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public DealStatus saveDealStatus(DealStatus dealStatus) throws DataBaseException {
        if (dealStatus.getId() == 0) {
            return dealStatusDAO.create(dealStatus);
        } else {
            dealStatusDAO.update(dealStatus);
            return dealStatusDAO.read(dealStatus.getId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public void addContactToDeal(Deal createdDeal, Contact contact) throws DataBaseException {
        DealContact dealContact = new DealContact();
        dealContact.setDeal(createdDeal);
        dealContact.setContact(contact);
        dealContactDAO.create(dealContact);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public void deleteContactFromDeal(int dealId, int contactId) throws DataBaseException {
        dealContactDAO.deleteDealContact(dealId, contactId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public void deleteDealFilter(int id) throws DataBaseException {
        filterDAO.delete(id);
    }

    public List<Tag> getAllDealTags() throws DataBaseException {
        return tagDAO.readAll(SubjectType.CONTACT_TAG);
    }

    @Override
    public List<Deal> findDealsByFilters(List<String> listOfFilters) throws DataBaseException {

        List<Deal> dealsList;
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
        dealsList = dealDAO.readAllWithConditions(listOfFilters);
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

