package com.becomejavasenior;

import com.becomejavasenior.config.DaoConfig;
import com.becomejavasenior.interfacedao.DealStatusDAO;
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
@ContextConfiguration(classes = {DaoConfig.class})
public class StatusDAOTest {
    @Autowired
    private DealStatusDAO dealStatusDAO;

    @Test
    public void getStatus() throws DataBaseException {
        DealStatus status = dealStatusDAO.read(1);

        Assert.assertEquals(1, status.getId());
        Assert.assertEquals("PRIMARY CONTACT", status.getName());
        Assert.assertEquals("#0040ff", status.getColor());
        Assert.assertFalse(status.isSystemDefault());
    }
}
