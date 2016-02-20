package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import com.becomejavasenior.interfacedao.TaskDAO;
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
@ContextConfiguration(classes = HibernateConfig.class)
public class TaskDAOTest {
    @Autowired
    TaskDAO taskDAO;

    @Test
    public void getAllBySubjectIdTest() throws DataBaseException {
        List<Task> list = taskDAO.getAllTasksBySubjectId(1);
        System.out.println(list);
    }

    @Test
    public void getSubjectDyIdTest() throws DataBaseException {
        Subject subject = taskDAO.getSubject(1);
        System.out.println(subject.getName());
    }

    @Test
    public void getTasksByParameters() throws DataBaseException {
        List<Task> list = taskDAO.getAllTasksByParameters("1", null, "2");
        System.out.println(list);
    }
}
