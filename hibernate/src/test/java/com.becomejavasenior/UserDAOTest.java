package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import com.becomejavasenior.interfacedao.UserDAO;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class UserDAOTest {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UserDAO userDAO;

    @Test
    public void readUserTest() throws DataBaseException {
        User user = (User) sessionFactory.openSession().get(User.class, 1);
        System.out.println(user);
        sessionFactory.close();
    }

    @Test
    public void getUserByIdTest() throws DataBaseException {
        User user = userDAO.read(1);
        System.out.println(user.getName());
    }

    @Test
    public void getUserByLoginTest() throws DataBaseException {
        User user = userDAO.getUserByLogin("mononofuu@gmail.com");
        System.out.println(user.getId());
    }

    @Test
    public void createAndDeleteTest() throws DataBaseException {
        User user = new User();
        user.setName("MOCK USER");
        user.setEmail("mock@mail.com");
        user.setLogin("LOGIN");
        user = userDAO.create(user);
        Assert.assertEquals(userDAO.getUserByLogin("LOGIN").getEmail(), "mock@mail.com");
        userDAO.delete(user.getId());
        Assert.assertNull(userDAO.read(user.getId()));
    }
}
