package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;
import com.becomejavasenior.User;

public interface UserDAO extends GenericDao<User>{
    User getUserByLogin(String login) throws DataBaseException;
}
