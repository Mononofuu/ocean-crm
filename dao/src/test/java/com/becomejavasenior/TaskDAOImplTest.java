package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.impl.UserDAOImpl;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAODataSourceConfig.class})
public class TaskDAOImplTest {
    private static final Logger LOGGER = LogManager.getLogger(TaskDAOImplTest.class);
    private static final String COMMENT_1 = "testcomment";
    private static final String COMMENT_2 = "changed comment";
    private static final String CONTACT_NAME = "test_contact_name";
    private static final String USER_NAME_1 = "test_user_name";
    private static final String USER_NAME_2 = "changed user name";
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final String DUE_DATE_1 = "04/20/2020";
    private static final String DUE_DATE_2 = "10/20/2020";
    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    private TaskDAO taskDao;
    @Autowired
    private ContactDAO contactDao;
    private Task task;
    private List<Object> objectsToDeleteFromDB = new ArrayList<>();

    @Before
    public void setUp() throws DataBaseException {
        task = new Task();
        task.setComment(COMMENT_1);
        task.setSubject(createContact());
        task.setDateCreated(new Date());
        task.setDueTime(getDueDate(DUE_DATE_1));
        task.setType(TaskType.MEETING);
        task.setUser(createUser(USER_NAME_1));
    }

    @Test
    public void createUpdateDeleteTest() throws DataBaseException {
        Task taskFromBD = taskDao.create(task);
        assertEquals(task.getComment(), taskFromBD.getComment());
        assertEquals(task.getDateCreated(), taskFromBD.getDateCreated());
        assertEquals(task.getDueTime(), taskFromBD.getDueTime());
        assertEquals(task.getSubject(), taskFromBD.getSubject());
        assertEquals(task.getType(), taskFromBD.getType());

        task.setComment(COMMENT_2);
        task.setDueTime(getDueDate(DUE_DATE_2));
        task.setUser(createUser(USER_NAME_2));
        taskFromBD.setComment(COMMENT_2);
        taskFromBD.setDueTime(getDueDate(DUE_DATE_2));
        taskFromBD.setUser(createUser(USER_NAME_2));
        taskDao.update(taskFromBD);
        taskFromBD = taskDao.read(taskFromBD.getId());

        List listBefore = taskDao.readAll();
        taskDao.delete(taskFromBD.getId());
        List listAfter = taskDao.readAll();
        assertEquals(listBefore.size(), listAfter.size() + 1);
    }

    @After
    public void removeDBEntries() throws DataBaseException {
        for (Object object : objectsToDeleteFromDB) {
            if (object instanceof User) {
                userDAO.delete(((User) object).getId());
            } else if (object instanceof Contact) {
                contactDao.delete(((Contact) object).getId());
            }
        }
    }

    private Contact createContact() throws DataBaseException {
        Contact contact = new Contact();
        contact.setName(CONTACT_NAME);
        contact = contactDao.create(contact);
        objectsToDeleteFromDB.add(contact);
        return contact;
    }

    private Date getDueDate(String dueDate) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date result = new Date();
        try {
            result = dateFormat.parse(dueDate);
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        return result;
    }

    private User createUser(String name) throws DataBaseException {
        User user = new User();
        user.setName(name);
        user = userDAO.create(user);
        objectsToDeleteFromDB.add(user);
        return user;
    }
}
