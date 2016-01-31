package com.becomejavasenior;

import com.becomejavasenior.config.DaoConfig;
import com.becomejavasenior.interfacedao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfig.class})
public class UserDaoTest {
    @Autowired
    UserDAO userDAO;

    @Test
    public void readAllTest() {
        try {
            List<User> userList = userDAO.readAll();
            for (User user : userList) {
                System.out.println(user.getId());
                System.out.println(user.getName());
                System.out.println(user.getPassword());
            }
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }
}
