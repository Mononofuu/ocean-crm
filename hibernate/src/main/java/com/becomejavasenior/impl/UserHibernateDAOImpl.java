package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.User;
import com.becomejavasenior.interfacedao.UserDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */

@Repository("HibernateUserDAO")
@Scope(value = "prototype")
public class UserHibernateDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {
    @Override
    public Class getObjectСlass() {
        return User.class;
    }

    @Override
    public User getUserByLogin(String login) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("login", login));
        return (User) criteria.uniqueResult();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Object persistentInstance = getCurrentSession().load(getObjectСlass(), id);
        if (persistentInstance != null) {
            getCurrentSession().delete(persistentInstance);
        }
    }
}
