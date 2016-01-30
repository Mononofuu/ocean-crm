package com.becomejavasenior;


import com.becomejavasenior.config.DaoConfig;
import com.becomejavasenior.interfacedao.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfig.class})
public class DealDAOTest {
    private final static String DEAL_NAME = "Test Deal";
    private final static String COMPANY_NAME = "Test Company";
    private final static String TAG = "testdealtag";
    private final static String CURRENCY_NAME = "Dollar";
    private final static String CURRENCY_CODE = "USD";
    private final static String COMPANY_PHONE = "0442222222";
    private final static String COMPANY_EMAIL = "test@test.com";
    private final static String COMPANY_ADDRESS = "Ukraine";
    private final static String COMPANY_WEB = "https://www.company.com.ua";
    private final static String CONTACT_PHONE = "0999999999";
    private Deal deal;
    @Autowired
    private DealStatusDAO dealStatusDAO;
    @Autowired
    private CurrencyDAO currencyDAO;
    @Autowired
    private DealDAO dealDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DealContactDAO dealContactDAO;

    @Test
    public void CreateUpdateDeleteTest() throws DataBaseException {
        deal = new Deal();
        deal.setName(DEAL_NAME);
        deal.setBudget(5000);

        Set<Tag> tags = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Tag tag = new Tag();
            tag.setName(TAG + i);
            tags.add(tag);
        }
        deal.setTags(tags);

        DealStatus status;
        status = dealStatusDAO.read(1);
        deal.setStatus(status);

        Currency currency = new Currency();
        currency.setName(CURRENCY_NAME);
        currency.setCode(CURRENCY_CODE);
        currency = currencyDAO.create(currency);
        deal.setCurrency(currency);


        deal.setDateWhenDealClose(null);
        deal.setDateCreated(new Timestamp(new Date().getTime()));

        Company company = new Company();
        company.setName(COMPANY_NAME);
        company.setPhoneNumber(COMPANY_PHONE);
        company.setEmail(COMPANY_EMAIL);
        company.setAdress(COMPANY_ADDRESS);
        try {
            company.setWeb(new URL(COMPANY_WEB));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        company = companyDAO.create(company);
        deal.setDealCompany(company);

        Contact contact = new Contact();
        contact.setName(COMPANY_NAME);
        contact.setPhoneType(PhoneType.WORK_PHONE_NUMBER);
        contact.setPhone(CONTACT_PHONE);
        contact.setCompany(company);
        contact = contactDAO.create(contact);

        Contact contact2 = new Contact();
        contact2.setName(COMPANY_NAME);
        contact2.setPhoneType(PhoneType.WORK_PHONE_NUMBER);
        contact2.setPhone(CONTACT_PHONE);
        contact2.setCompany(company);
        contact2 = contactDAO.create(contact2);


        User user = userDAO.read(1);
        deal.setMainContact(contact);

        Deal dbDeal = dealDAO.create(deal);

        DealContact dealContact = new DealContact();
        dealContact.setDeal(dbDeal);
        dealContact.setContact(contact);

        DealContact dealContact2 = new DealContact();
        dealContact2.setDeal(dbDeal);
        dealContact2.setContact(contact2);

        List<Contact> dealContactList = new ArrayList<>();
        dealContactList.add(contact);
        dealContactList.add(contact2);

        dealContactDAO.create(dealContact);
        dealContactDAO.create(dealContact2);

        assertEquals(deal, dbDeal);

        List<Contact> createdDealContactList = dealContactDAO.getAllContactsBySubjectId(dbDeal.getId());
        Assert.assertEquals(dealContactList.size(), createdDealContactList.size());
    }

    @Test
    public void readAllTest() throws DataBaseException {
        long start = System.nanoTime();
        dealDAO.readAllLite();
        long time = (System.nanoTime() - start) / 1_000_000;
        System.out.println(time + " ms");
        Assert.assertTrue(time < 10_000);
    }


}
