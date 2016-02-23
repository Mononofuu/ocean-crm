package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
@Transactional
@ActiveProfiles(profiles = "test")
public class TaskDAOTest {
    private final static String TASK_COMMENT = "Task comment";

    @Autowired
    @Qualifier(value = "HibernateTaskDAO")
    TaskDAO taskDAO;

    @Test
    public void crudTaskTest() throws DataBaseException {
        Task task = new Task();
        task.setComment(TASK_COMMENT);
        task.setType(TaskType.MEETING);
        task.setIsClosed((byte) 0);

        task = taskDAO.create(task);
        Assert.assertEquals(1, task.getId());
        Assert.assertEquals(TASK_COMMENT, task.getComment());

        task.setIsClosed((byte) 1);
        taskDAO.update(task);
        task = taskDAO.read(task.getId());
        Assert.assertEquals(1, task.getId());
        Assert.assertEquals(1, task.getIsClosed());

        taskDAO.delete(task.getId());
        task = taskDAO.read(task.getId());
        Assert.assertNull(task);
    }

}
