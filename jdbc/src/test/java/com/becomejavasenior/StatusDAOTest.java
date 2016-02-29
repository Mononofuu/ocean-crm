package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAODataSourceConfig.class})
@ActiveProfiles("test")
public class StatusDAOTest {
    private final static int DEAL_STATUS_ID = 1;
    @Autowired
    DealStatusDAO dealStatusDAO;

    @Test
    public void getStatus() throws DataBaseException {
        DealStatus status = dealStatusDAO.read(DEAL_STATUS_ID);

        Assert.assertEquals(1, status.getId());
        Assert.assertEquals("PRIMARY CONTACT", status.getName());
        Assert.assertEquals("#0040ff", status.getColor());
        Assert.assertFalse(status.isSystemDefault());
    }
}
