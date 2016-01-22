package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Contact;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;
import com.becomejavasenior.ContactFilters;
import java.util.Date;
import java.util.List;

public interface ContactDAO extends GenericDao<Contact>{
    Contact readContactByName(String name) throws DataBaseException;
    List<Contact> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException;
}
