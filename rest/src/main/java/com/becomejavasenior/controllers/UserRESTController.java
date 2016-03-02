package com.becomejavasenior.controllers;

import com.becomejavasenior.ServiceException;
import com.becomejavasenior.User;
import com.becomejavasenior.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RestController
@RequestMapping("/rest/users")
public class UserRESTController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() throws ServiceException {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) throws ServiceException {
        return userService.findUserById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void addUser(@RequestBody User user) throws ServiceException {
        userService.saveUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void editUser(@PathVariable int id, @RequestBody User user) throws ServiceException {
        userService.saveUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) throws ServiceException {
        userService.deleteUser(id);
    }
}
