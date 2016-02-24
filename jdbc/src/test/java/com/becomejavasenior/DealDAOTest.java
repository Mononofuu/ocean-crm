package com.becomejavasenior;


import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAODataSourceConfig.class})
@ActiveProfiles("test")
public class DealDAOTest {
    private final static String DEAL_NAME = "Test Deal";
    private final static int DOLLAR_ID = 1;
    private final static int DEAL_STATUS_ID = 1;

    @Autowired
    private DealStatusDAO dealStatusDAO;
    @Autowired
    private CurrencyDAO currencyDAO;
    @Autowired
    private DealDAO dealDAO;

    @Test
    public void createUpdateDeleteTest() throws DataBaseException {
        Deal deal = new Deal();
        deal.setName(DEAL_NAME);
        deal.setBudget(5000);
        DealStatus status;
        status = dealStatusDAO.read(DEAL_STATUS_ID);
        deal.setStatus(status);
        Currency currency = currencyDAO.read(DOLLAR_ID);
        deal.setCurrency(currency);
        Deal dbDeal = dealDAO.create(deal);
        assertEquals(deal, dbDeal);
    }

    @Test
    public void readAllTest() throws DataBaseException {
        createUpdateDeleteTest();
        List<Deal> dealList = dealDAO.readAllLite();
        assertEquals(dealList.size(), 1);
    }

}
