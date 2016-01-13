package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Contact;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.ContactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
public class ContactServiceImpl implements ContactService{

    static final Logger logger = LogManager.getRootLogger();
    ContactDAO ContactDao = new ContactDAOImpl();

    @Override
    public void saveContact(Contact contact) throws DataBaseException {
        if(contact.getId() == 0){
            ContactDao.create(contact);
        }else{
            ContactDao.update(contact);
        }
    }

    @Override
    public void deleteContact(int id) throws DataBaseException {
        ContactDao.delete(id);
    }

    @Override
    public Contact findContactById(int id) throws DataBaseException {
        Contact contact = ContactDao.read(id);
        return contact;
    }

    @Override
    public List<Contact> findContacts() throws DataBaseException{
        List<Contact> contactList = ContactDao.readAll();
        return contactList;
    }

    @Override
    public List<Contact> findContactsLite() throws DataBaseException{
        List<Contact> contactList = ContactDao.readAllLite();
        return contactList;
    }

}
