package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.config.ServiceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.becomejavasenior.TaskType.FOLLOW_UP;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, DAODataSourceConfig.class})
@ActiveProfiles("test")
public class TaskTypeServiceTest {
    @Autowired
    public TaskTypeService taskTypeService;

    @Test
    public void readTest() throws DataBaseException {
        TaskType taskType = taskTypeService.findTaskTypeById(1);

        Assert.assertEquals(taskType, FOLLOW_UP);
    }

}
