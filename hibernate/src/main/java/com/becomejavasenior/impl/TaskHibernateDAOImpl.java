package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Repository
public class TaskHibernateDAOImpl extends AbstractHibernateDAO<Task> implements TaskDAO {
    @Override
    public Class getObjectСlass() {
        return Task.class;
    }

    @Override
    public List<Task> getAllTasksBySubjectId(int id) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Task.class);
//        criteria.add(Restrictions.eq("subject", getSubject(id)));
        return criteria.list();
    }

    @Override
    public List<Task> getAllTasksByParameters(String userId, Date date, String taskTypeId) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Task.class);
        //todo!
        return criteria.list();
    }

    @Override
    public List<Task> getAllTasksBySubject(Subject subject) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Task.class);
        criteria.add(Restrictions.eq("subject", subject));
        return criteria.list();
    }

//    @Override
//    public Subject getSubject(int id) throws DataBaseException {
//        Criteria criteria = getCurrentSession().createCriteria(Subject.class);
//        criteria.add(Restrictions.eq("id", id));
//        return (Subject) criteria.uniqueResult();
//    }

    @Override
    public void delete(int id) throws DataBaseException {
        Object persistentInstance = getCurrentSession().load(getObjectСlass(), id);
        if (persistentInstance != null) {
            getCurrentSession().delete(persistentInstance);
        }
    }
}
