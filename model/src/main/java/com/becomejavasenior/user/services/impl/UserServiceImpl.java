package com.becomejavasenior.user.services.impl;

import com.becomejavasenior.user.dto.User;
import com.becomejavasenior.user.services.IncorrectDataException;
import com.becomejavasenior.user.services.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static Map <String, User> miniBD;
    static {
        miniBD = new HashMap<>();
        User user1 = new User();
        user1.setLogin("admin@admin.org");
        user1.setPassword("admin");
        user1.setRole("admin");
        User user2 = new User();
        user2.setLogin("user@user.org");
        user2.setPassword("user");
        user2.setRole("user");
        miniBD.put("admin@admin.org", user1);
        miniBD.put("user@user.org", user2);

    }

    public User login(String login, String pass) throws IncorrectDataException {
        User user = miniBD.get(login);
        if(miniBD.containsKey(login) && user.getPassword().equals(pass)){
            return user;
        } else {
            throw new IncorrectDataException("incorrect login ot pass");
        }
    }

    public void registration(String login, String name) {
        System.out.println("registred " + login + " " + name);
    }
}
