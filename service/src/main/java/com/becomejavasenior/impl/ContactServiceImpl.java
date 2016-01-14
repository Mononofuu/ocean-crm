package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
public class ContactServiceImpl implements ContactService{

    static final Logger logger = LogManager.getRootLogger();
    private DaoFactory dao;
    private ContactDAOImpl contactDao;

    public ContactServiceImpl() throws DataBaseException {
        dao = new PostgreSqlDaoFactory();
        contactDao = (ContactDAOImpl)dao.getDao(Contact.class);
    }

    @Override
    public void saveContact(Contact contact) throws DataBaseException {
        if(contact.getId() == 0){
            contactDao.create(contact);
        }else{
            contactDao.update(contact);
        }
    }

    @Override
    public void deleteContact(int id) throws DataBaseException {
        contactDao.delete(id);
    }

    @Override
    public Contact findContactById(int id) throws DataBaseException {
        Contact contact = contactDao.read(id);
        return contact;
    }

    @Override
    public List<Contact> findContacts() throws DataBaseException{
        List<Contact> contactList = contactDao.readAll();
        return contactList;
    }

    @Override
    public List<Contact> findContactsLite() throws DataBaseException{
        List<Contact> contactList = contactDao.readAllLite();
        return contactList;
    }

}
