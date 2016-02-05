package com.becomejavasenior;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface CommentService {
    Comment findCommentById(int id) throws DataBaseException;

    Comment saveComment(Comment comment) throws DataBaseException;

    void deleteComment(int id) throws DataBaseException;

    List<Comment> findComments() throws DataBaseException;

    List<Comment> findCommentsBySubjectId(int id) throws DataBaseException;

    Subject getSubject(int id) throws DataBaseException;

}
