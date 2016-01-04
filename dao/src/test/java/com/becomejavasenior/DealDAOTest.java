package com.becomejavasenior;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
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
    private GenericDao<Deal> dealDao;
    private Deal deal;
    private PostgreSqlDaoFactory daoFactory;

    @Before
    public void SetUp() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
        dealDao = daoFactory.getDao(Deal.class);
    }

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
        GenericDao<DealStatus> statusDao = daoFactory.getDao(DealStatus.class);
        status = statusDao.read(1);
        deal.setStatus(status);

        Currency currency = new Currency();
        currency.setName(CURRENCY_NAME);
        currency.setCode(CURRENCY_CODE);
        GenericDao<Currency> currencyDao = daoFactory.getDao(Currency.class);
        currency = currencyDao.create(currency);
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
        GenericDao<Company> companyDao = daoFactory.getDao(Company.class);
        company = companyDao.create(company);
        deal.setDealCompany(company);

        Contact contact = new Contact();
        contact.setName(COMPANY_NAME);
        contact.setPhoneType(PhoneType.WORK_PHONE_NUMBER);
        contact.setPhone(CONTACT_PHONE);
        contact.setCompany(company);
        GenericDao<Contact> contactDao = daoFactory.getDao(Contact.class);
        contact = contactDao.create(contact);
        deal.setMainContact(contact);
        Deal dbDeal = dealDao.create(deal);
        assertEquals(deal, dbDeal);
    }

    @Test
    public void readAllTest() throws DataBaseException {
        long start = System.nanoTime();
        dealDao.readAllLite();
        long time = (System.nanoTime() - start) / 1_000_000;
        System.out.println(time + " ms");
        Assert.assertTrue(time < 10_000);
    }


}
