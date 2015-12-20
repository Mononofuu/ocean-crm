package com.becomejavasenior;

import org.junit.Assert;
import org.junit.Test;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class StatusDAOTest {
    @Test
    public void getStatus() throws DataBaseException {
        DaoFactory daoFactory = new PostgreSqlDaoFactory();
        GenericDao statusDao = daoFactory.getDao(DealStatus.class);
        DealStatus status = (DealStatus) statusDao.read(1);

        Assert.assertEquals(1, status.getId());
        Assert.assertEquals("PRIMARY CONTACT", status.getName());
        Assert.assertEquals("#0040ff", status.getColor());
        Assert.assertFalse(status.isSystemDefault());
    }
}
