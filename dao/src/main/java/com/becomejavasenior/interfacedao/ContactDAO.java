package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Contact;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

public interface ContactDAO extends GenericDao<Contact>{
    Contact readContactByName(String name) throws DataBaseException;
}
