package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CommentDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class CommentServiceImpl implements CommentService {
    static final Logger logger = LogManager.getRootLogger();
    DaoFactory daoFactory;
    CommentDAO commentDAO;

    public CommentServiceImpl() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
        commentDAO = (CommentDAO) daoFactory.getDao(Comment.class);
    }

    @Override
    public Comment findCommentById(int id) throws DataBaseException {
        return commentDAO.read(id);
    }

    @Override
    public Comment saveComment(Comment comment) throws DataBaseException {
        if (comment.getId() == 0) {
            return commentDAO.create(comment);
        } else {
            commentDAO.update(comment);
            return commentDAO.read(comment.getId());
        }
    }

    @Override
    public void deleteComment(int id) throws DataBaseException {
        commentDAO.delete(id);
    }

    @Override
    public List<Comment> findComments() throws DataBaseException {
        return commentDAO.readAll();
    }
}
