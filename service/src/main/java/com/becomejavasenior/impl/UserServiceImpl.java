package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(TaskTypeServiceImpl.class);
    private DaoFactory dao;
    private GenericDao<User> userDao;

    public UserServiceImpl(){
        try {
            dao = new PostgreSqlDaoFactory();
            userDao = dao.getDao(User.class);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public User saveUser(User user) throws DataBaseException {
        if(user.getId() == 0){
            return userDao.create(user);
        }else{
            userDao.update(user);
            return userDao.read(user.getId());
        }
    }

    @Override
    public void deleteUser(int id) throws DataBaseException {
        userDao.delete(id);
    }

    @Override
    public User findUserById(int id) throws DataBaseException {
        return userDao.read(id);
    }

    @Override
    public List<User> getAllUsers() throws DataBaseException {
        return userDao.readAll();
    }
}
