package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import com.becomejavasenior.interfacedao.CompanyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class CompanyDAOTest {
    @Autowired
    CompanyDAO companyHibernateDAO;

    @Test
    public void readCompanyTest() throws DataBaseException {
        Company company = companyHibernateDAO.read(1);
        System.out.println(company);
    }
}
