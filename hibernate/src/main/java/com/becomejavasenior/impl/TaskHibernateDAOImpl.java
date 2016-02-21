package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Repository
@Scope(value = "prototype")
public class TaskHibernateDAOImpl extends AbstractHibernateDAO<Task> implements TaskDAO {
    @Override
    public Class getObjectСlass() {
        return Task.class;
    }

    @Override
    public List<Task> getAllTasksBySubjectId(int id) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Task.class);
        criteria.add(Restrictions.eq("subject.id", id));
        return criteria.list();
    }

    @Override
    public List<Task> getAllTasksByParameters(String userId, Date date, String taskTypeId) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Task.class);
        if (userId != null) {
            criteria.add(Restrictions.eq("user", getCurrentSession().get(User.class, Integer.parseInt(userId))));
        }
        if (date != null) {
            criteria.add(Restrictions.eq("dueTime", date));
        }
        if (taskTypeId != null) {
            criteria.add(Restrictions.eq("type", TaskType.values()[Integer.parseInt(taskTypeId)]));
        }
        return criteria.list();
    }

    @Override
    public List<Task> getAllTasksBySubject(Subject subject) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Task.class);
        criteria.add(Restrictions.eq("subject", subject));
        return criteria.list();
    }

    @Override
    public Subject getSubject(int id) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Subject.class);
        criteria.add(Restrictions.eq("id", id));
        return (Subject) criteria.uniqueResult();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Object persistentInstance = getCurrentSession().load(getObjectСlass(), id);
        if (persistentInstance != null) {
            getCurrentSession().delete(persistentInstance);
        }
    }
}
