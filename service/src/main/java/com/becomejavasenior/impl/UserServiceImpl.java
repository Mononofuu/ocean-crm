package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Role;
import com.becomejavasenior.User;
import com.becomejavasenior.UserService;
import com.becomejavasenior.interfacedao.GrantsDAO;
import com.becomejavasenior.interfacedao.RoleDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GrantsDAO grantsDAO;

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

    @Override
    public List<User> getAllUsersLite() throws DataBaseException {
        return userDAO.readAllLite();
    }

    @Override
    public User getUserByLogin(String login) throws DataBaseException {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public Role getUserRole(User user) throws DataBaseException {
        return grantsDAO.read(user.getId()).getRole();
    }
}
