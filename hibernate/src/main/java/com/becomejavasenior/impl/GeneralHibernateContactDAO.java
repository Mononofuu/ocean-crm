package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Tag;
import com.becomejavasenior.interfacedao.GeneralContactDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hsqldb.Expression;

import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class GeneralHibernateContactDAO<T> extends AbstractHibernateDAO<T> implements GeneralContactDAO<T>{

    public T readContactByName(String name) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("name", name));
        return (T)criteria.uniqueResult();
    }

    public int findTotalEntryes() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.setProjection(Projections.rowCount());
        return (int)criteria.uniqueResult();
    }

    public List<T> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        if(userId!=null){
            criteria.add(Restrictions.eq("user", Integer.parseInt(userId)));
        }
        if(parameters!=null&&!parameters.isEmpty()){
            createRestrictionsByParameters(criteria, parameters);//TODO
        }
        if(tagIdList!=null&&!tagIdList.isEmpty()){
            createRestrictionsByTags(criteria, tagIdList);//TODO
        }
        if(taskDate!=null&&taskDate.size()==2){
            createRestrictionsByTaskDate(criteria, taskDate);//TODO
        }
        if(createUpdateDate!=null&&createUpdateFlag!=null&&createUpdateDate.size()==2){
            createRestrictionsByCreateUpdateDate(criteria, createUpdateDate, createUpdateFlag);//TODO
        }
        return (List<T>) criteria.list();
    }

    private void createRestrictionsByCreateUpdateDate(Criteria criteria, List<Date> createUpdateDate, String createUpdateFlag){

    }

    private void createRestrictionsByTaskDate(Criteria criteria, List<Date> taskDate){

    }

    private void createRestrictionsByTags(Criteria criteria, List<Integer> tagIdList){
        criteria.createAlias("tags", "tg");
        for(Integer tagId: tagIdList){

        }
    }

    private void createRestrictionsByParameters(Criteria criteria, List<ContactFilters> parameters){
        if(parameters.contains(ContactFilters.WITHOUT_TASKS)){
        }
        if(parameters.contains(ContactFilters.WITH_OVERDUE_TASKS)){
        }
        if(parameters.contains(ContactFilters.WITHOUT_DEALS)){
        }
        if(parameters.contains(ContactFilters.WITHOUT_OPEN_DEALS)){
        }
        if(parameters.contains(ContactFilters.PRIMARY_CONTACTS)){
        }
        if(parameters.contains(ContactFilters.CONVERSATION_CONTACTS)){
        }
        if(parameters.contains(ContactFilters.MAKING_DECISION_CONTACTS)){
        }
        if(parameters.contains(ContactFilters.APPROVAL_CONTRACT_CONTACTS)){
        }
        if(parameters.contains(ContactFilters.SUCCESS_CONTACTS)){
        }
        if(parameters.contains(ContactFilters.NOT_REALISED_CONTACTS)){
        }
        if(parameters.contains(ContactFilters.DELETED_CONTACTS)){
        }
    }
}
