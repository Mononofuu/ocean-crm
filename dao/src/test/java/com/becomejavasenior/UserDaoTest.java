package com.becomejavasenior;

import com.becomejavasenior.impl.UserDAOImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan
public class UserDaoTest {
    @Autowired
    UserDAOImpl userDAO;

    public void readAllTest(){
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
