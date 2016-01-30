package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.User;
import com.becomejavasenior.UserService;
import com.becomejavasenior.interfacedao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public UserServiceImpl() {
    }

    @Override
    public User saveUser(User user) throws DataBaseException {
        if (user.getId() == 0) {
            return userDAO.create(user);
        } else {
            userDAO.update(user);
            return userDAO.read(user.getId());
        }
    }

    @Override
    public void deleteUser(int id) throws DataBaseException {
        userDAO.delete(id);
    }

    @Override
    public User findUserById(int id) throws DataBaseException {
        return userDAO.read(id);
    }

    @Override
    public List<User> getAllUsers() throws DataBaseException {
        return userDAO.readAll();
    }
}
