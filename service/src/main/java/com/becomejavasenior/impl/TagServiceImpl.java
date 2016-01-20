package com.becomejavasenior.impl;

import com.becomejavasenior.*;
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

    public TagServiceImpl() {
        try {
            dao = new PostgreSqlDaoFactory();
            tagDao = (TagDAOImpl)dao.getDao(Tag.class);
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
}
