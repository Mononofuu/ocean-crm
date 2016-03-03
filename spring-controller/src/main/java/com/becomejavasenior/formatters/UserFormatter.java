package com.becomejavasenior.formatters;

import com.becomejavasenior.ServiceException;
import com.becomejavasenior.User;
import com.becomejavasenior.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by kramar on 01.03.16.
 */
@Component
public class UserFormatter implements Formatter<User> {

    private static final Logger LOGGER = LogManager.getLogger(DealStatusFormatter.class);

    @Autowired
    private UserService userService;

    @Override
    public User parse(String id, Locale arg1) throws ParseException {
        try {
            User user = userService.findUserById(Integer.parseInt(id));
            user.setComments(null);
            user.setEvents(null);
            user.setFiles(null);
            user.setRoles(null);
            user.setTasks(null);
            return user;
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public String print(User user, Locale locale) {
        return user.getName();
    }
}
