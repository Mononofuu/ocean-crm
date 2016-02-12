package com.becomejavasenior.impl;

import com.becomejavasenior.Contact;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class ContactHibernateDAOImpl extends GeneralHibernateContactDAO<Contact> implements ContactDAO{
    private static final Logger LOGGER = LogManager.getLogger(ContactHibernateDAOImpl.class);

    @Override
    public Contact readContactByName(String name) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Contact.class);
        criteria.add(Restrictions.eq("name", name));
        return (Contact)criteria.uniqueResult();
    }

    @Override
    public Class getObjectСlass() {
        return Contact.class;
    }

    @Override
    public List<Contact> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        return null;
    }

    @Override
    public int findTotalEntryes() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Contact.class);
        criteria.setProjection(Projections.rowCount());
        return (int)criteria.uniqueResult();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Contact contact = new Contact();
        contact.setId(id);
        delete(contact);
    }
}
