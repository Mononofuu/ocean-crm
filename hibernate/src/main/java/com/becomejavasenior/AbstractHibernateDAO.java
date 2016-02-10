package com.becomejavasenior;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class AbstractHibernateDAO {
    @Autowired
    SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
