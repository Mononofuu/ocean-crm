package com.becomejavasenior.impl;

import com.becomejavasenior.Comment;
import com.becomejavasenior.CommentService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.interfacedao.CommentDAO;
import com.becomejavasenior.interfacedao.SubjectDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Service
public class CommentServiceImpl implements CommentService {
    static final Logger logger = LogManager.getRootLogger();
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private SubjectDAO subjectDAO;

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

    @Override
    public List<Comment> findCommentsBySubjectId(int id) throws DataBaseException {
        return commentDAO.getAllCommentsBySubjectId(id);
    }

    @Override
    public Subject getSubject(int id) throws DataBaseException {
        return subjectDAO.read(id);
    }
}
