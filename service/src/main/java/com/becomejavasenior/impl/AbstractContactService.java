package com.becomejavasenior.impl;

import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class AbstractContactService<T> {
    private static Logger logger = LogManager.getLogger(AbstractContactService.class);

    protected abstract AbstractContactDAO<T> getDao();

    public List<T> getAllContactsByParameters(Map<String, String[]> parameters) throws DataBaseException {
        List<ContactFilters> parametersList = new ArrayList<>();
        String userId = null;
        List<Integer> tagIdList = new ArrayList<>();
        Date taskStartDate = null;
        Date taskDueDate = null;
        String filter = parameters.get("filtername")[0];
        switch (filter){
            case "overduetaskcontacts":
                parametersList.add(ContactFilters.WITH_OVERDUE_TASKS);
                break;
            case "tasklesscontacts":
                parametersList.add(ContactFilters.WITHOUT_TASKS);
                break;
            default:
                break;
        }
        String user = parameters.get("user")[0];
        if(!"".equals(user)){
            userId=user;
        }
        String[] dealfilters = parameters.get("dealfilters");
        parametersList.addAll(parseDealFilters(dealfilters));
        String[] tags = parameters.get("tags");
        if(tags!=null){
            for(String tagId: tags){
                tagIdList.add(Integer.parseInt(tagId));
            }
        }
        String period = parameters.get("tasks")[0];
        if(!"".equals(period)){
            switch (period){
                case "notasks":
                    parametersList.add(ContactFilters.WITHOUT_TASKS);
                    break;
                case "overdue":
                    parametersList.add(ContactFilters.WITH_OVERDUE_TASKS);
                    break;
                default:
                    List<Date> dates = setStartAndDueDates(period);
                    taskStartDate = dates.get(0);
                    taskDueDate = dates.get(1);
            }
        }
        logger.debug(taskStartDate);
        logger.debug(taskDueDate);
        return getDao().getAllContactsByParameters(parametersList, userId, tagIdList, taskStartDate, taskDueDate);
    }

    private List<ContactFilters> parseDealFilters(String[] filters){
        List<ContactFilters> result = new ArrayList<>();
        if(filters!=null&&filters.length>0){
            for(String dealFilter: filters){
                switch (dealFilter){
                    case "withoutdeal":
                        result.add(ContactFilters.WITHOUT_DEALS);
                        break;
                    case "withoutopendeal":
                        result.add(ContactFilters.WITHOUT_OPEN_DEALS);
                        break;
                    case "firstcontact":
                        result.add(ContactFilters.PRIMARY_CONTACTS);
                        break;
                    case "discussion":
                        result.add(ContactFilters.CONVERSATION_CONTACTS);
                        break;
                    case "takingdesision":
                        result.add(ContactFilters.MAKING_DECISION_CONTACTS);
                        break;
                    case "agreement":
                        result.add(ContactFilters.APPROVAL_CONTRACT_CONTACTS);
                        break;
                    case "success":
                        result.add(ContactFilters.SUCCESS_CONTACTS);
                        break;
                    case "closedandnotrealised":
                        result.add(ContactFilters.NOT_REALISED_CONTACTS);
                }
            }
        }
        return result;
    }

    private List<Date> setStartAndDueDates(String period){
        Date startDate = null;
        Date dueDate = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        switch (period){
            case "today":
                startDate = c.getTime();
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MINUTE, -1);
                dueDate = c.getTime();
                break;
            case "tomorow":
                c.add(Calendar.DAY_OF_MONTH, 1);
                startDate = c.getTime();
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MINUTE, -1);
                dueDate = c.getTime();
                break;
            case "thisweek":
                c.set(Calendar.DAY_OF_WEEK, 0);
                startDate = c.getTime();
                c.add(Calendar.DAY_OF_MONTH, 7);
                c.add(Calendar.MINUTE, -1);
                dueDate = c.getTime();
                break;
            case "thismonth":
                c.set(Calendar.DAY_OF_MONTH, 0);
                startDate = c.getTime();
                c.add(Calendar.MONTH, 1);
                c.add(Calendar.MINUTE, -1);
                dueDate = c.getTime();
                break;
            case "thisquoter":
                c.set(Calendar.DAY_OF_MONTH, 0);
                startDate = getQuarterStart(c).getTime();
                c.add(Calendar.MONTH, 3);
                c.add(Calendar.MINUTE, -1);
                dueDate = c.getTime();
                break;
        }
        List<Date> result = new ArrayList<>();
        result.add(startDate);
        result.add(dueDate);
        return result;
    }

    private Calendar getQuarterStart(Calendar date)
    {
        int quarter = date.get(Calendar.MONTH)/3+1;
        date.set(Calendar.DAY_OF_MONTH, 0);
        date.set(Calendar.MONTH, 0);
        switch (quarter){
            case 2:
                date.add(Calendar.MONTH, 3);
            case 3:
                date.add(Calendar.MONTH, 3);
            case 4:
                date.add(Calendar.MONTH, 3);
        }
        return date;
    }
}
