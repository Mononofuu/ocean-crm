package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAODataSourceConfig.class})
public class ContactDAOImplTest {
    @Autowired
    private ContactDAO contactDao;
    @Autowired
    private CompanyDAO companyDAO;
    private Contact contact;

    @Before
    public void setUp() throws DataBaseException {
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
            tag.setSubjectType(SubjectType.CONTACT_TAG);
            contactTags.add(tag);
        }
        contact.setTags(contactTags);
        Company company = new Company();
        company.setName("testcompanyname");
        company.setPhoneNumber("testcompanuphone");
        company.setEmail("testcompanyemail");
        company.setAddress("setcompanyaddress");
        Set<Tag> companyTags = new HashSet<>();
        for (int i = 3; i < 6; i++) {
            Tag tag = new Tag();
            tag.setName("testtag" + i);
            tag.setSubjectType(SubjectType.COMPANY_TAG);
            companyTags.add(tag);
        }
        company.setTags(companyTags);
        try {
            company.setWeb(new URL("https://www.google.com.ua"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        company = companyDAO.create(company);
        contact.setCompany(company);
    }

    @Test
    public void createUpdateDeleteTest() throws DataBaseException {
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
        companyDAO.delete(contactFromDB.getCompany().getId());
    }
}
