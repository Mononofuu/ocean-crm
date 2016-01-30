package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
@Service
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
    public Deal saveDeal(Deal deal) throws DataBaseException {
        if(deal.getId() == 0){
            return dealDAO.create(deal);
        }else{
            dealDAO.update(deal);
            return dealDAO.read(deal.getId());
        }
    }

    @Override
    public void deleteDeal(int id) throws DataBaseException {
        dealDAO.delete(id);
    }

    @Override
    public Deal findDealById(int id) throws DataBaseException {
        Deal deal = dealDAO.read(id);
        return deal;
    }

    @Override
    public List<Deal> findDeals() throws DataBaseException{
        List<Deal> dealList = dealDAO.readAll();
        return dealList;
    }

    @Override
    public List<Deal> findDealsLite() throws DataBaseException{
        List<Deal> dealList = dealDAO.readAllLite();
        return dealList;
    }

    @Override
    public List<Deal> findDealsByStatus(int statusId) throws DataBaseException {
        List<Deal> dealList = dealDAO.readStatusFilter(Integer.valueOf(statusId));
        return dealList;
    }

    @Override
    public List<Deal> findDealsByUser(int userId) throws DataBaseException {
        List<Deal> dealList = dealDAO.readUserFilter(Integer.valueOf(userId));
        return dealList;
    }

    @Override
    public List<Deal> findDealsByTags(String tag) throws DataBaseException {
        List<Deal> dealList = dealDAO.readTagFilter("'" + tag + "')))");
        return dealList;
    }

    @Override
    public List<Deal> findDealsByConditions(int condition) throws DataBaseException {
        List<Deal> dealList = dealDAO.readAllWithConditions(condition);
        return dealList;
    }

    @Override
    public List<Deal> findDealsByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        List<Deal> dealList = dealDAO.readAllByCreatedDateInterval(dateBegin, dateEnd);
        return dealList;
    }

    @Override
    public List<Deal> findDealsByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        List<Deal> dealList = dealDAO.readAllByTasksDueDateInterval(dateBegin, dateEnd);
        return dealList;
    }

    @Override
    public List<Deal> findDealsByFilters(List<String> listOfFilters) throws DataBaseException {
        List<Deal> dealsList = new ArrayList<Deal>();
        List<List<Deal>> list = new ArrayList<List<Deal>>();
        for (String item : listOfFilters) {
            switch (item) {
                case "selectedfilter_open":
                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_OPENED);
                    list.add(dealsList);
                    break;
                case "selectedfilter_success":
                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_SUCCESS);
                    list.add(dealsList);
                    break;
                case "selectedfilter_fail":
                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_CLOSED_AND_NOT_IMPLEMENTED);
                    list.add(dealsList);
                    break;
                case "selectedfilter_notask":
                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_WITHOUT_TASKS);
                    list.add(dealsList);
                    break;
                case "selectedfilter_expired":
                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_WITH_EXPIRED_TASKS);
                    list.add(dealsList);
                    break;
                case "selectedfilter_deleted":
                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_DELETED);
                    list.add(dealsList);
                    break;
                default:
                    if (item.startsWith("selectedfilter_my")) {
                        int userId = Integer.parseInt(item.replace("selectedfilter_my_", ""));
                        dealsList = findDealsByUser(userId);
                        list.add(dealsList);
                    } else if (item.startsWith("when")) {
                        SimpleDateFormat formatter = new SimpleDateFormat();
                        formatter.applyPattern("mm/dd/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        java.util.Date currentDate = cal.getTime();
                        Date dateBegin = new java.sql.Date(currentDate.getTime());
                        Date dateEnd = dateBegin;
                        boolean useFilter = true;
                        switch (item) {
                            case "when_TODAY":
                                break;
                            case "when_FOR_THREE_DAYS":
                                cal.add(Calendar.DATE, -2);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                break;
                            case "when_WEEK":
                                cal.add(Calendar.DATE, -6);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                break;
                            case "when_MONTH":
                                cal.add(Calendar.MONTH, -1);
                                cal.add(Calendar.DATE, 1);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                break;
                            case "when_QUARTER":
                                cal.add(Calendar.MONTH, -3);
                                cal.add(Calendar.DATE, 1);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                break;
                            case "when_PERIOD":
                                String dateFrom = item.substring(12, 22);
                                String dateTo = item.substring(23, 33);
                                if (!dateFrom.equals("") && !dateTo.equals("")) {
                                    java.util.Date date = null;
                                    try {
                                        date = formatter.parse(dateFrom);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    dateBegin = new java.sql.Date(date.getTime());
                                    try {
                                        date = formatter.parse(dateTo);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    dateEnd = new java.sql.Date(date.getTime());
                                } else {
                                    logger.error("Bad date in filter in DealsListServlet");
                                    useFilter = false;
                                }
                                break;
                            default:
                                logger.info("Uncnown condition in created date filter in DealsListServlet");
                                useFilter = false;
                                break;
                        }
                        if (useFilter) {
                            dealsList = findDealsByCreatedDateInterval(dateBegin, dateEnd);
                            list.add(dealsList);
                        }
                    } else if (item.startsWith("phase")) {
                        int dealStatusId = Integer.parseInt(item.replace("phase_", ""));
                        dealsList = findDealsByStatus(dealStatusId);
                        list.add(dealsList);
                    } else if (item.startsWith("manager")) {
                        int userId = Integer.parseInt(item.replace("manager_", ""));
                        dealsList = findDealsByUser(userId);
                        list.add(dealsList);
                    } else if (item.startsWith("tasks")) {
                        SimpleDateFormat formatter = new SimpleDateFormat();
                        formatter.applyPattern("mm/dd/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        java.util.Date currentDate = cal.getTime();
                        Date dateBegin = new java.sql.Date(currentDate.getTime());
                        Date dateEnd = dateBegin;
                        boolean useFilter = true;
                        item = item.replace("tasks_","");
                        switch (item){
                            case "TODAY":
                                break;
                            case "TOMORROW":
                                cal.add(Calendar.DATE,1);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                dateEnd = dateBegin;
                                break;
                            case "THIS_WEEK":
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
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                cal.add(Calendar.DATE,6);
                                currentDate = cal.getTime();
                                dateEnd = new java.sql.Date(currentDate.getTime());
                                break;
                            case "THIS_MONTHS":
                                cal.set(Calendar.DAY_OF_MONTH, 1);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                cal.add(Calendar.MONTH,1);
                                cal.add(Calendar.DATE,-1);
                                currentDate = cal.getTime();
                                dateEnd = new java.sql.Date(currentDate.getTime());
                                break;
                            case "THIS_QUARTER":
                                cal.set(Calendar.DAY_OF_MONTH, 1);
                                cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH)%3);
                                currentDate = cal.getTime();
                                dateBegin = new java.sql.Date(currentDate.getTime());
                                cal.add(Calendar.MONTH,3);
                                cal.add(Calendar.DATE,-1);
                                currentDate = cal.getTime();
                                dateEnd = new java.sql.Date(currentDate.getTime());
                                break;
                            case "WO_TASKS":
                                break;
                            case "EXPIRED":
                                break;
                            default:
                                logger.info("Uncnown condition in created date filter in DealsListServlet");
                                useFilter = false;
                                break;
                        }
                        if(useFilter) {
                            switch (item) {
                                case "WO_TASKS":
                                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_WITHOUT_TASKS);
                                    break;
                                case "EXPIRED":
                                    dealsList = findDealsByConditions(DealService.CONDITION_DEALS_WITH_EXPIRED_TASKS);
                                    break;
                                default:
                                    dealsList = findDealsByTasksDueDateInterval(dateBegin, dateEnd);
                                    break;
                            }
                            list.add(dealsList);
                        }
                    } else if (item.startsWith("tags")) {
                        item = item.replace("tags_", "");
                        String tag = item.trim().replaceAll("\\s+", "','");
                        if (!tag.equals("")) {
                            dealsList = findDealsByTags(tag);
                            list.add(dealsList);
                        }
                    }
                break;
            }
        }
        if (list.size() == 0) {
            return findDeals();
        } else {
            Iterator<List<Deal>> listIterator = list.iterator();
            dealsList = listIterator.next();
            while (dealsList.size() != 0 && listIterator.hasNext()) {
                dealsList.retainAll(listIterator.next());
            }
            return dealsList;
        }
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
    public void deleteDealStatus(int id) throws DataBaseException {
        dealStatusDAO.delete(id);
    }

    @Override
    public DealStatus saveDealStatus(DealStatus dealStatus) throws DataBaseException {
        if (dealStatus.getId() == 0) {
            return dealStatusDAO.create(dealStatus);
        } else {
            dealStatusDAO.update(dealStatus);
            return dealStatusDAO.read(dealStatus.getId());
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
        return tagDAO.readAll(SubjectType.CONTACT_TAG);
    }
}
