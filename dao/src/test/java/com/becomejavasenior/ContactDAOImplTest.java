package com.becomejavasenior;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ContactDAOImplTest {
    private DaoFactory daoFactory;
    private Connection connection;
    private GenericDao<Contact> contactDao;
    private Contact contact;

    @Before
    public void SetUp() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
        connection = daoFactory.getConnection();
        contactDao = daoFactory.getDao(connection, Contact.class);
        contact = new Contact();
        contact.setName("testname");
        contact.setPost("testpost");
        contact.setPhoneType(PhoneType.MOBILE_PHONE_NUMBER);
        contact.setPhone("testphone");
        contact.setEmail("testemail");
        contact.setSkype("testskype");
        Set<Tag> contactTags = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Tag tag = new Tag();
            tag.setName("testtag" + i);
            contactTags.add(tag);
        }
        contact.setTags(contactTags);
        Company company = new Company();
        company.setName("testcompanyname");
        company.setPhoneNumber("testcompanuphone");
        company.setEmail("testcompanyemail");
        company.setAdress("setcompanyaddress");
        Set<Tag> companyTags = new HashSet<>();
        for (int i = 3; i < 6; i++) {
            Tag tag = new Tag();
            tag.setName("testtag" + i);
            companyTags.add(tag);
        }
        company.setTags(companyTags);
        try {
            company.setWeb(new URL("https://www.google.com.ua"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        GenericDao<Company> companyDao = daoFactory.getDao(connection, Company.class);
        company = companyDao.create(company);
        contact.setCompany(company);
        //List<Comment> comments = new ArrayList<>();


    }

    @Test
    public void CreateUpdateDeleteTest() throws DataBaseException {
        Contact contactFromDB = contactDao.create(contact);
        assertEquals(contact, contactFromDB);
        contact.setName("changename");
        contactFromDB.setName("changename");
        contactDao.update(contactFromDB);
        contactFromDB = contactDao.read(contactFromDB.getId());
        assertEquals(contact, contactFromDB);
        List listBefore = contactDao.readAll();
        contactDao.delete(contactFromDB.getId());
        List listAfter = contactDao.readAll();
        assertEquals(listBefore.size(), listAfter.size() + 1);
        GenericDao<Company> companyDao = daoFactory.getDao(connection, Company.class);
        companyDao.delete(contactFromDB.getCompany().getId());
    }

}
