package com.becomejavasenior;

import java.util.List;
import java.util.Map;


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
        List<Contact> getAllContactsByParameters(Map<String, String[]> parameters) throws DataBaseException;
        List<Contact> getAllContactsBySubjectId(int id) throws DataBaseException;
        List<Tag> getAllContactTags() throws DataBaseException;
}
