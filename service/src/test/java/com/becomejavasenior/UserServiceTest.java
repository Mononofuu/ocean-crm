package com.becomejavasenior;

import com.becomejavasenior.config.DaoConfig;
import com.becomejavasenior.config.DataSourceConfig;
import com.becomejavasenior.config.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {ServiceConfig.class, DataSourceConfig.class, DaoConfig.class})
public class UserServiceTest {
    @Autowired
    @Qualifier("userServiceImpl")
    public UserService userService;

    @Test
    public void readAllTest() {
        User newUser = new User();
        newUser.setName("SERG");
        newUser.setLogin("login");
        newUser.setPassword("password");

        List<User> userList = null;
        try {
            newUser = userService.saveUser(newUser);
            userList = userService.getAllUsers();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        System.out.println(newUser.getId() + "/////////////////////////");

        System.out.println("***************************");
        for (User user : userList) {
            System.out.println(user.getId());
            System.out.println(user.getName());
            System.out.println(user.getPassword());
        }
        System.out.println("***************************");

    }
}
