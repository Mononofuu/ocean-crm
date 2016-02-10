package com.becomejavasenior;

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
public class ContactHibernateDAOImpl extends AbstractHibernateDAO implements ContactDAO{
    @Override
    public Contact readContactByName(String name) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Contact.class);
        criteria.add(Restrictions.eq("name", name));
        return null;
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
    public Contact create(Contact object) throws DataBaseException {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public Contact read(int key) throws DataBaseException {
        return (Contact)getCurrentSession().get(Contact.class, key);
    }

    @Override
    public Contact readLite(int key) throws DataBaseException {
        return (Contact)getCurrentSession().get(Contact.class, key);
    }

    @Override
    public void update(Contact object) throws DataBaseException {
        getCurrentSession().update(object);
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Contact contact = new Contact();
        contact.setId(id);
        getCurrentSession().delete(contact);
    }

    @Override
    public List<Contact> readAll() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Contact.class);
        return (List<Contact>)criteria.list();
    }

    @Override
    public List<Contact> readAllLite() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Contact.class);
        return (List<Contact>)criteria.list();
    }
}
