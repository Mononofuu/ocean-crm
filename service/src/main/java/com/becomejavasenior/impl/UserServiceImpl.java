package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.GrantsDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier(value = "HibernateUserDAO")
    private UserDAO userDAO;
    @Autowired
    private GrantsDAO grantsDAO;

    public UserServiceImpl() {
    }

    @Override
    public User saveUser(User user) throws ServiceException {
        try{
            if (user.getId() == 0) {
                return userDAO.create(user);
            } else {
                userDAO.update(user);
                return userDAO.read(user.getId());
            }
        }catch (DataBaseException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(int id) throws ServiceException {
        try {
            userDAO.delete(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserById(int id) throws ServiceException {
        try {
            return userDAO.read(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDAO.readAll();
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsersLite() throws ServiceException {
        try {
            return userDAO.readAllLite();
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            return userDAO.getUserByLogin(login);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Role getUserRole(User user) throws ServiceException {
        try {
            return grantsDAO.read(user.getId()).getRole();
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }
}
