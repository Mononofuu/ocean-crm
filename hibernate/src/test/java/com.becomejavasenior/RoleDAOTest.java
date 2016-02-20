package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class RoleDAOTest {
    @Autowired
    SessionFactory sessionFactory;

    @Test
    @Transactional
    public void createRoleTest() {
        Role role = new Role();
        role.setName("TestRole");

        int id = (int) sessionFactory.getCurrentSession().save(role);
        System.out.println(id);
    }
}
