package com.becomejavasenior.interfaceservice;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.User;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserService {
    void saveUser(User user) throws DataBaseException;
    void deleteUser(int id) throws DataBaseException;
    User findUserById(int id) throws DataBaseException;
    List<User> getAllUsers() throws DataBaseException;
}
