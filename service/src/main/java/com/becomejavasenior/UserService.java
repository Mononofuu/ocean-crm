package com.becomejavasenior;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserService {
    User saveUser(User user) throws ServiceException;
    void deleteUser(int id) throws ServiceException;
    User findUserById(int id) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
    List<User> getAllUsersLite() throws ServiceException;
    User getUserByLogin(String login) throws ServiceException;
    Role getUserRole(User user) throws ServiceException;
}
