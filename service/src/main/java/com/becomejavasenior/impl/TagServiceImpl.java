package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.SubjectTagDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class TagServiceImpl implements TagService {
    private static Logger logger = LogManager.getLogger(TagServiceImpl.class);
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private SubjectTagDAO subjectTagDAO;

    @Override
    public Tag saveTag(Tag tag) throws DataBaseException {
        if (tag.getId() == 0) {
            return tagDAO.create(tag);
        } else {
            tagDAO.update(tag);
            return tagDAO.read(tag.getId());
        }
    }

    @Override
    public void deleteTag(int id) throws DataBaseException {
        tagDAO.delete(id);
    }

    @Override
    public Tag findTagById(int id) throws DataBaseException {
        return tagDAO.read(id);
    }

    @Override
    public List<Tag> getAllTags() throws DataBaseException {
        return tagDAO.readAll();
    }

    @Override
    public List<Tag> getAllTagsBySubjectId(int id) throws DataBaseException {
        return tagDAO.readAllSubjectTags(id);
    }

    @Override
    public void addTagToSubject(Subject subject, Tag tag) throws DataBaseException {
        List<Tag> existedTags = tagDAO.readAllSubjectTags(subject.getId());
        if (existedTags.stream().filter(tag1 -> tag1.getName().equals(tag)).count() < 1) {
            Tag returnedTag = tagDAO.create(tag);
            logger.info(String.format("Trying to create tag: %s", tag.getName()));
            SubjectTag subjectTag = new SubjectTag();
            subjectTag.setTag(returnedTag);
            subjectTag.setSubject(subject);
            subjectTagDAO.create(subjectTag);
        }
    }
}
