package com.becomejavasenior;

import com.becomejavasenior.config.DataSourceConfig;
import com.becomejavasenior.impl.UserTemplateDAOImpl;
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
@ContextConfiguration(classes = {DataSourceConfig.class})
public class UserDAOTest {
    @Autowired
    public UserTemplateDAOImpl userTemplateDAO;

    @Test
    public void testReadAll() throws Exception {
        List<User> userList = userTemplateDAO.readAll();
        for (User user : userList) {
            System.out.println(user.getId());
            System.out.println(user.getName());
            System.out.println(user.getPassword());
        }

    }
}