package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.TagDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class TagHibernateDAOImpl extends AbstractHibernateDAO<Tag> implements TagDAO {
    @Override
    public Class getObjectСlass() {
        return Tag.class;
    }

    @Override
    public int checkIfExists(Tag tag) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Tag.class)
                .add(Restrictions.eq("name", tag.getName()))
                .add(Restrictions.eq("subjectType", tag.getSubjectType()));
        Tag result = (Tag)criteria.uniqueResult();
        if(result!=null){
            return result.getId();
        }else {
            return -1;
        }
    }

    @Override
    public List<Tag> readAllSubjectTags(int subjectId) throws DataBaseException {
        String s = "from com.becomejavasenior.Tag t left join com.becomejavasenior.SubjectTag st where t.id = st.tag.id and st.subject.id = :subjectId";
        Query query = getCurrentSession().createQuery(s);
        query.setParameter("subjectId", subjectId);
        return query.list();
    }

    @Override
    public List<Tag> readAll(SubjectType subjectType) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Tag.class);
        criteria.add(Restrictions.eq("subjectType", subjectType));
        return criteria.list();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Tag tag = new Tag();
        tag.setId(id);
        delete(tag);
    }
}
