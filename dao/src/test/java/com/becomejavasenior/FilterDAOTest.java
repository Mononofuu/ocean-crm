package com.becomejavasenior;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class FilterDAOTest {


    private GenericDao<Filter> filterDao;
    private Filter filter;
    private Filter returned;

    @Before
    public void setUp() throws DataBaseException {
        DaoFactory factory = new PostgreSqlDaoFactory();
        filterDao = factory.getDao(Filter.class);
        filter = new Filter();

        filter.setUser((User) factory.getDao(User.class).read(1));

        filter.setType(FilterPeriod.PERIOD);
        filter.setDate_from(Timestamp.valueOf("2015-12-15 10:10:10.0"));
        filter.setDate_to(new Timestamp(new Date().getTime()));

        filter.setTaskType(FilterTaskType.IGNORE);

        GenericDao<DealStatus> statusDao = factory.getDao(DealStatus.class);
        filter.setStatus(statusDao.read(1));

        GenericDao<Contact> contactDao = factory.getDao(Contact.class);
        filter.setManager(contactDao.read(2));
    }


    @Test
    public void createNewFilter() throws DataBaseException {
        returned = filterDao.create(filter);
        assertEquals(filter, returned);

        System.out.println(returned.getId());
        System.out.println(returned.getUser().getName());
        System.out.println(returned.getType().name());
        System.out.println(returned.getDate_from());
        System.out.println(returned.getDate_to());
        System.out.println(returned.getStatus().getName());
        System.out.println(returned.getManager().getName());
        System.out.println(returned.getTaskType().name());
        System.out.println(returned.getTags());

        assertEquals(filterDao.read(returned.getId()), returned);
        returned.setType(FilterPeriod.TODAY);
        filterDao.update(returned);
        assertEquals(filterDao.read(returned.getId()).getType(), FilterPeriod.TODAY);
        filterDao.delete(returned.getId());
        assertNull(filterDao.read(returned.getId()));
    }
}
