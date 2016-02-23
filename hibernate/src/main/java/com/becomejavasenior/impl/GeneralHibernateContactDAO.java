package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.GeneralContactDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class GeneralHibernateContactDAO<T> extends AbstractHibernateDAO<T> implements GeneralContactDAO<T>{

    public T readContactByName(String name) {
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
            //TODO filtering by user id
        }
        if(parameters!=null&&!parameters.isEmpty()){
            createRestrictionsByParameters(criteria, parameters);
        }
        if(tagIdList!=null&&!tagIdList.isEmpty()){
            criteria.createCriteria("tags")
                    .add(Restrictions.in("id", tagIdList));
        }
        if(taskDate!=null&&taskDate.size()==2){
            createRestrictionsByTaskDate(criteria, taskDate);//TODO filtering by task date
        }
        if(createUpdateDate!=null&&createUpdateFlag!=null&&createUpdateDate.size()==2){
            criteria.add(Restrictions.between(createUpdateFlag+"Date", createUpdateDate.get(0), createUpdateDate.get(1)));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<T>) criteria.list();
    }

    private void createRestrictionsByTaskDate(Criteria criteria, List<Date> taskDate){
    }


    private void createRestrictionsByParameters(Criteria criteria, List<ContactFilters> parameters){
        if(parameters.contains(ContactFilters.WITHOUT_TASKS)){
            //TODO
        }
        if(parameters.contains(ContactFilters.WITH_OVERDUE_TASKS)){
            //TODO
        }
        if(parameters.contains(ContactFilters.WITHOUT_DEALS)){
            criteria.add(Restrictions.isEmpty("deals"));
        }
        if(parameters.contains(ContactFilters.WITHOUT_OPEN_DEALS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.in("name", new String[]{
                            "SUCCESS",
                            "CLOSED AND NOT IMPLEMENTED"
                    }));
        }
        if(parameters.contains(ContactFilters.PRIMARY_CONTACTS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.eq("name", "PRIMARY CONTACT"));
        }
        if(parameters.contains(ContactFilters.CONVERSATION_CONTACTS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.eq("name", "CONVERSATION"));
        }
        if(parameters.contains(ContactFilters.MAKING_DECISION_CONTACTS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.eq("name", "MAKE THE DECISION"));
        }
        if(parameters.contains(ContactFilters.APPROVAL_CONTRACT_CONTACTS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.eq("name", "APPROVAL OF THE CONTRACT"));
        }
        if(parameters.contains(ContactFilters.SUCCESS_CONTACTS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.eq("name", "SUCCESS"));
        }
        if(parameters.contains(ContactFilters.NOT_REALISED_CONTACTS)){
            criteria.createCriteria("deals")
                    .createCriteria("status")
                    .add(Restrictions.eq("name", "CLOSED AND NOT IMPLEMENTED"));
        }
        if(parameters.contains(ContactFilters.DELETED_CONTACTS)){
            criteria.add(Restrictions.eq("removed", true));
        }
    }
}
