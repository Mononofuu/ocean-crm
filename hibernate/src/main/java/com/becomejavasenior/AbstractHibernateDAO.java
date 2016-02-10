package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Transactional
public abstract class AbstractHibernateDAO<T> implements GenericDao<T>{
    private static final Logger LOGGER = LogManager.getLogger(AbstractHibernateDAO.class);
    @Autowired
    SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T create(T object) throws DataBaseException {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public T read(int key) throws DataBaseException {
        return (T)getCurrentSession().get(getObjectСlass(), key);
    }

    @Override
    public T readLite(int key) throws DataBaseException {
        return (T)getCurrentSession().get(getObjectСlass(), key);
    }

    @Override
    public void update(T object) throws DataBaseException {
        getCurrentSession().update(object);
    }

    @Override
    public List<T> readAll() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        return (List<T>)criteria.list();
    }

    @Override
    public List<T> readAllLite() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        return (List<T>)criteria.list();
    }

    public void delete(Object object) throws DataBaseException {
        getCurrentSession().delete(object);
    }

    public abstract Class getObjectСlass();
}
