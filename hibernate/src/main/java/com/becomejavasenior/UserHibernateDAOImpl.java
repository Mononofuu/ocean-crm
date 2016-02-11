package com.becomejavasenior;

import com.becomejavasenior.interfacedao.UserDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */
public class UserHibernateDAOImpl extends AbstractHibernateDAO<User> implements UserDAO{


    @Override
    public Class getObjectСlass() {
        return User.class;
    }

    @Override
    public void delete(int id) throws DataBaseException {

    }

}
