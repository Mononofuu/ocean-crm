package com.becomejavasenior.impl;

import com.becomejavasenior.Contact;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.SubjectType;
import com.becomejavasenior.Tag;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class ContactHibernateDAOImpl extends GeneralHibernateContactDAO<Contact> implements ContactDAO{

    @Override
    public Class getObjectСlass() {
        return Contact.class;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Contact contact = new Contact();
        contact.setId(id);
        delete(contact);
    }

    @Override
    public List<Tag> readAllContactsTags() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Tag.class);
        criteria.add(Restrictions.eq("subjectType", SubjectType.CONTACT_TAG));
        return criteria.list();
    }
}
