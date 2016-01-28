package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.User;
import com.becomejavasenior.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(TaskTypeServiceImpl.class);
    @Autowired
    private UserTemplateDAOImpl userDao;

    public UserServiceImpl() {
    }

    @Override
    public User saveUser(User user) throws DataBaseException {
        if (user.getId() == 0) {
            return userDao.create(user);
        } else {
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
