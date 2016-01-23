package com.becomejavasenior;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserService {
    User saveUser(User user) throws DataBaseException;
    void deleteUser(int id) throws DataBaseException;
    User findUserById(int id) throws DataBaseException;
    List<User> getAllUsers() throws DataBaseException;
}
