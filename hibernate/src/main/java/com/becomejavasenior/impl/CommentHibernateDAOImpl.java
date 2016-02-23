package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.Comment;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CommentDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Repository("HibernateCommentDAO")
@Scope(value = "prototype")
public class CommentHibernateDAOImpl extends AbstractHibernateDAO<Comment> implements CommentDAO{

    @Override
    public Class getObjectСlass() {
        return Comment.class;
    }

    @Override
    public List<Comment> getAllCommentsBySubjectId(int id) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectСlass());
        criteria.add(Restrictions.eq("subject.id", id));
        return criteria.list();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Object persistentInstance = getCurrentSession().load(getObjectСlass(), id);
        if (persistentInstance != null) {
            getCurrentSession().delete(persistentInstance);
        }
    }
}
