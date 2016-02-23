package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
@Service
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ContactServiceImpl extends AbstractContactService<Contact> implements ContactService{
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private PhoneTypeDAO phoneTypeDAO;
    @Autowired
    private DealContactDAO dealContactDAO;

    @Override
    protected GeneralContactDAO<Contact> getDao() {
        return contactDAO;
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public Contact saveContact(Contact contact) throws DataBaseException {
        if (contact.getId() == 0) {
            contact = contactDAO.create(contact);
            return contact;
        } else {
            contactDAO.update(contact);
            return contactDAO.read(contact.getId());
        }
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public void deleteContact(int id) throws DataBaseException {
        contactDAO.delete(id);
    }

    @Override
    public Contact findContactById(int id) throws DataBaseException {
        Contact contact = contactDAO.read(id);
        return contact;
    }

    @Override
    public List<Contact> findContacts() throws DataBaseException{
        List<Contact> contactList = contactDAO.readAll();
        return contactList;
    }

    @Override
    public List<Contact> findContactsLite() throws DataBaseException{
        List<Contact> contactList = contactDAO.readAllLite();
        return contactList;
    }

    @Override
    public Contact findContactByName(String name) throws DataBaseException {
        return contactDAO.readContactByName(name);
    }

    @Override
    public List<PhoneType> getAllPhoneTypes() throws DataBaseException {
        return phoneTypeDAO.readAll();
    }

    @Override
    public List<Contact> getAllContactsBySubjectId(int id) throws DataBaseException {
        return dealContactDAO.getAllContactsBySubjectId(id);
    }

    @Override
    public List<Tag> getAllContactTags() throws DataBaseException {
        return contactDAO.readAllContactsTags();
    }
}
