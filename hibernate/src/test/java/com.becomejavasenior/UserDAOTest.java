package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import com.becomejavasenior.interfacedao.UserDAO;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
@Transactional
@ActiveProfiles(profiles = "test")
public class UserDAOTest {
    private final static int ADDED_USERS_IN_DB = 2;

    @Autowired
    @Qualifier(value = "HibernateUserDAO")
    UserDAO userDAO;

    @Test
    public void readAllUsersTest() throws DataBaseException {
        List<User> userList = userDAO.readAll();
        Assert.assertEquals(ADDED_USERS_IN_DB, userList.size());
    }

    @Test
    public void getUserByIdTest() throws DataBaseException {
        User user = userDAO.read(1);
        Assert.assertEquals("user1", user.getLogin());
    }

    @Test
    public void getUserByLoginTest() throws DataBaseException {
        User user = userDAO.getUserByLogin("user2");
        Assert.assertEquals(2, user.getId());
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
