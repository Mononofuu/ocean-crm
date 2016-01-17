package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.UserService;
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
    public void saveUser(User user) throws DataBaseException {
        if(user.getId() == 0){
            userDao.create(user);
        }else{
            userDao.update(user);
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
