package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Contact;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealContact;
import com.becomejavasenior.GenericDao;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealContactDAO extends GenericDao<DealContact> {
    List<Contact> getAllContactsBySubjectId(int id) throws DataBaseException;
    void deleteDealContact(int dealId, int contactId) throws DataBaseException;
}
