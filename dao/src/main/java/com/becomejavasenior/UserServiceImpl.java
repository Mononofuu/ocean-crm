package com.becomejavasenior;

import com.becomejavasenior.exception.IncorrectDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    public User login(String login, String pass) throws IncorrectDataException {
        User user = getUser(login);
        if (user == null) {
            LOGGER.error("unknown user");
            throw new IncorrectDataException("unknown user");
        }
        if (!user.getPassword().equals(pass)) {
            LOGGER.error("incorrect login or password");
            throw new IncorrectDataException("incorrect login ot pass");
        }
        return user;
    }

    private User getUser(String login) {
        List users = new ArrayList<>();
        try {
            users = new PostgreSqlDaoFactory().getDao(User.class).readAll();
        } catch (DataBaseException e) {
            LOGGER.error(e.getMessage());
        }
        for (Object user : users) {
            if (user instanceof User) {
                if (((User) user).getLogin().equals(login)) {
                    return (User) user;
                }
            }
        }
        return null;
    }

    public void registration(String login, String name) {
        System.out.println("registred " + login + " " + name);
    }
}
