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

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, DAODataSourceConfig.class})
@ActiveProfiles("test")
public class CurrencyServiceTest {
    @Autowired
    public CurrencyService currencyService;

    @Test
    public void readTest() throws DataBaseException {
        Currency currency = currencyService.findCurrencyById(1);
        Assert.assertEquals(currency.getName(), "Dollar");
    }

}
