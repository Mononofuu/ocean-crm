package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.Contact;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class ContactHibernateDAOImpl extends AbstractHibernateDAO<Contact> implements ContactDAO{

    @Override
    public Class getObjectСlass() {
        return Contact.class;
    }

    @Override
    public Contact readContactByName(String name) throws DataBaseException {
        return null;
    }

    @Override
    public List<Contact> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        return null;
    }

    @Override
    public int findTotalEntryes() throws DataBaseException {
        return 0;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Contact contact = new Contact();
        contact.setId(id);
        delete(contact);
    }

}
