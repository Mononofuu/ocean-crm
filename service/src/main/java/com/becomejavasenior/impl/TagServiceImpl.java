package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.SubjectTagDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TagServiceImpl implements TagService {
    private static Logger logger = LogManager.getLogger(TagServiceImpl.class);
    private DaoFactory dao;
    private TagDAO tagDao;
    private SubjectTagDAO subjectTagDAO;

    public TagServiceImpl() {
        try {
            dao = new PostgreSqlDaoFactory();
            tagDao = (TagDAOImpl) dao.getDao(Tag.class);
            subjectTagDAO = (SubjectTagDAOImpl) dao.getDao(SubjectTag.class);
        } catch (DataBaseException e) {
            logger.error(e);
        }
    }

    @Override
    public Tag saveTag(Tag tag) throws DataBaseException {
        if (tag.getId() == 0) {
            return tagDao.create(tag);
        } else {
            tagDao.update(tag);
            return tagDao.read(tag.getId());
        }
    }

    @Override
    public void deleteTag(int id) throws DataBaseException {
        tagDao.delete(id);
    }

    @Override
    public Tag findTagById(int id) throws DataBaseException {
        return tagDao.read(id);
    }

    @Override
    public List<Tag> getAllTags() throws DataBaseException {
        return tagDao.readAll();
    }

    @Override
    public List<Tag> getAllTagsBySubjectId(int id) throws DataBaseException {
        return tagDao.readAllSubjectTags(id);
    }

    @Override
    public void addTagToSubject(Subject subject, Tag tag) throws DataBaseException {
        List<Tag> existedTags = tagDao.readAllSubjectTags(subject.getId());
        if (existedTags.stream().filter(tag1 -> tag1.getName().equals(tag)).count() < 1) {
            Tag returnedTag = tagDao.create(tag);
            logger.info(String.format("Trying to create tag: %s", tag.getName()));
            SubjectTag subjectTag = new SubjectTag();
            subjectTag.setTag(returnedTag);
            subjectTag.setSubject(subject);
            subjectTagDAO.create(subjectTag);
        }
    }
}
