package com.becomejavasenior.impl;

import com.becomejavasenior.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class AbstractContactDAO<T> extends AbstractJDBCDao<T> {

    public AbstractContactDAO(DaoFactory daoFactory) {
        super(daoFactory);
    }

    protected abstract String getLeftJoinTask();

    protected abstract String getLeftJoinDeal();

    protected abstract String getLeftJoinSubjectTag();

    public List<T> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, Date taskStartDate, Date taskDueDate) throws DataBaseException {
        return realiseQuery(getParametrisedReadQuery(parameters, userId, tagIdList, taskStartDate, taskDueDate));
    }

    protected String getParametrisedReadQuery(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, Date taskStartDate, Date taskDueDate){
        StringBuilder result = new StringBuilder(getReadAllQuery());
        Set<String> joinQueries = new HashSet<>();//заполняется JOIN запроссами
        Set<String> whereQueries = new HashSet<>();//заполняется WHERE условиями
        if(parameters!=null&&parameters.size()>0){
            if(parameters.contains(ContactFilters.WITHOUT_TASKS)){
                fillSets(joinQueries, whereQueries, getContactsWithoutTasksQuery());
            }
            if(parameters.contains(ContactFilters.WITH_OVERDUE_TASKS)){
                fillSets(joinQueries, whereQueries, getContactsWithOverdueTasksQuery());
            }
            if(parameters.contains(ContactFilters.WITHOUT_DEALS)){
                fillSets(joinQueries, whereQueries, getContactsWithoutDealsQuery());
            }
            if(parameters.contains(ContactFilters.WITHOUT_OPEN_DEALS)){
                fillSets(joinQueries, whereQueries, getContactsWithoutOpenDealsQuery());
            }
            if(parameters.contains(ContactFilters.PRIMARY_CONTACTS)){
                fillSets(joinQueries, whereQueries, getPrimaryContactsQuery());
            }
            if(parameters.contains(ContactFilters.CONVERSATION_CONTACTS)){
                fillSets(joinQueries, whereQueries, getConversationContactsQuery());
            }
            if(parameters.contains(ContactFilters.MAKING_DECISION_CONTACTS)){
                fillSets(joinQueries, whereQueries, getMakingDecisionContactsQuery());
            }
            if(parameters.contains(ContactFilters.APPROVAL_CONTRACT_CONTACTS)){
                fillSets(joinQueries, whereQueries, getApprovalContractContactsQuery());
            }
            if(parameters.contains(ContactFilters.SUCCESS_CONTACTS)){
                fillSets(joinQueries, whereQueries, getSuccessContactsQuery());
            }
            if(parameters.contains(ContactFilters.NOT_REALISED_CONTACTS)){
                fillSets(joinQueries, whereQueries, getNotRealisedContactsQuery());
            }
        }
        if(userId!=null){
            whereQueries.add(" AND content_owner_id = "+userId);
        }
        if(tagIdList!=null&&tagIdList.size()>0){
            fillSets(joinQueries, whereQueries, getContactsByTagsQuery(tagIdList));
        }
        if(taskStartDate!=null&&taskDueDate!=null){
            fillSets(joinQueries, whereQueries, getContactsByTaskPeriod(taskStartDate, taskDueDate));
        }
        for(String join: joinQueries){
            result.append(join);
        }
        result.append(" WHERE 1=1");
        for(String where: whereQueries){
            result.append(where);
        }
        return result.toString();
    }

    private void fillSets(Set<String> joinQueries, Set<String> whereQueries, String[] queries){
        joinQueries.add(queries[0]);
        whereQueries.add(queries[1]);
    }

    private String[] getContactsWithoutTasksQuery(){
        String[] result = {getLeftJoinTask(), " AND subject_id is NULL"};
        return result;
    }

    private String[] getContactsWithOverdueTasksQuery(){
        String[] result = {getLeftJoinTask(), " AND task.due_date < NOW()"};
        return result;
    }

    private String[] getContactsWithoutDealsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.contact_main_id is NULL"};
        return result;
    }

    private String[] getContactsWithoutOpenDealsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id ON (5, 6)"};
        return result;
    }

    private String[] getPrimaryContactsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id = 1"};
        return result;
    }

    private String[] getConversationContactsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id = 2"};
        return result;
    }

    private String[] getMakingDecisionContactsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id = 3"};
        return result;
    }

    private String[] getApprovalContractContactsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id = 4"};
        return result;
    }

    private String[] getSuccessContactsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id = 5"};
        return result;
    }

    private String[] getNotRealisedContactsQuery(){
        String[] result = {getLeftJoinDeal(), " AND deal.status_id = 6"};
        return result;
    }

    private String[] getContactsByTagsQuery(List<Integer> tagIdList){
        String[] result = {getLeftJoinSubjectTag(), " AND subject_tag.tag_id IN ("};
        for(Integer id: tagIdList){
            result[1]+=id+",";
        }
        result[1] = result[1].substring(0, result[1].length()-1);
        result[1]+=")";
        return result;
    }

    private String[] getContactsByTaskPeriod(Date taskStartDate, Date taskDueDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String andStatment = " AND task.due_date BETWEEN '"+dateFormat.format(taskStartDate)+"' AND '"+dateFormat.format(taskDueDate)+"'";
        String[] result = {getLeftJoinTask(), andStatment};
        return  result;
    }
}
