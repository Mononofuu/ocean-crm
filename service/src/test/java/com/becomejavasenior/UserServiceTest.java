package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.config.ServiceConfig;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, DAODataSourceConfig.class})
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    public UserService userService;

    @Test
    public void readAllTest() {
        User newUser = new User();
        newUser.setName("SERG");
        newUser.setLogin("login");
        newUser.setPassword("password");

        List<User> userList = null;
        int size = 0;
        try {
            userList = userService.getAllUsers();
            size = userList.size();
            userService.saveUser(newUser);
            userList = userService.getAllUsers();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(userList.size() == size + 1);
    }
}
