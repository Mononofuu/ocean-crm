package com.becomejavasenior;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Contact;

import java.util.List;


/**
 * Created by Peter on 18.12.2015.
 */
public interface ContactService {

        Contact findContactById(int id) throws DataBaseException;
        Contact saveContact(Contact contact) throws DataBaseException;
        void deleteContact(int id) throws DataBaseException;
        List<Contact> findContacts() throws DataBaseException;
        List<Contact> findContactsLite() throws DataBaseException;
        Contact findContactByName(String name) throws DataBaseException;
        List<PhoneType> getAllPhoneTypes() throws DataBaseException;
}
