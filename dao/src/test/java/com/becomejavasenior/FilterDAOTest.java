package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import com.becomejavasenior.interfacedao.FilterDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAODataSourceConfig.class})
public class FilterDAOTest {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private FilterDAO filterDAO;
    @Autowired
    private DealStatusDAO dealStatusDAO;
    @Autowired
    private ContactDAO contactDAO;

    private Filter filter;
    private Filter returned;

    @Before
    public void setUp() throws DataBaseException {
        filter = new Filter();

        filter.setName("Test filter");

        filter.setUser(userDAO.read(1));

        filter.setType(FilterPeriod.PERIOD);
        filter.setDate_from(Timestamp.valueOf("2015-12-15 10:10:10.0"));
        filter.setDate_to(new Timestamp(new Date().getTime()));

        filter.setTaskType(FilterTaskType.IGNORE);

        filter.setStatus(dealStatusDAO.read(1));

        filter.setManager(contactDAO.read(2));
    }


    @Test
    public void createNewFilter() throws DataBaseException {
        returned = filterDAO.create(filter);
        assertEquals(filter, returned);

        System.out.println(returned.getName());
        System.out.println(returned.getId());
        System.out.println(returned.getUser().getName());
        System.out.println(returned.getType().name());
        System.out.println(returned.getDate_from());
        System.out.println(returned.getDate_to());
        System.out.println(returned.getStatus().getName());
        System.out.println(returned.getTaskType().name());
        System.out.println(returned.getTags());

        assertEquals(filterDAO.read(returned.getId()), returned);
        returned.setType(FilterPeriod.TODAY);
        filterDAO.update(returned);
        assertEquals(filterDAO.read(returned.getId()).getType(), FilterPeriod.TODAY);
        filterDAO.delete(returned.getId());
        assertNull(filterDAO.read(returned.getId()));
    }
}
