package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.SubjectDAO;
import com.becomejavasenior.interfacedao.SubjectTagDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class SubjectTemplateDAOImpl<T> extends JdbcDaoSupport {
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private SubjectTagDAO subjectTagDAO;

    protected <T extends Subject> int createSubject(T object) throws DataBaseException {
        if (object instanceof Subject) {
            Subject subject = subjectDAO.create(object);
            createTags(object.getTags(), subject);
            return subject.getId();
        } else {
            throw new DataBaseException();
        }
    }

    private  <T extends Subject> void createTags(Set<Tag> tags, T object) throws DataBaseException {
        if (tags == null) {
            return;
        } else {
            for (Tag tag : tags) {
                SubjectTag subjectTag = new SubjectTag();
                if(object instanceof Contact){
                    tag.setSubjectType(SubjectType.CONTACT_TAG);
                }else if(object instanceof Company){
                    tag.setSubjectType(SubjectType.COMPANY_TAG);
                }else if(object instanceof Deal){
                    tag.setSubjectType(SubjectType.DEAL_TAG);
                }
                tag = tagDAO.create(tag);
                subjectTag.setTag(tag);
                subjectTag.setSubject(object);
                subjectTagDAO.create(subjectTag);
            }
        }
    }
}
