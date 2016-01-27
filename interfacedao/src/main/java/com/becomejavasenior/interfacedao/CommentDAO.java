package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Comment;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

import java.util.List;

public interface CommentDAO extends GenericDao<Comment>{

    public List<Comment> getAllCommentsBySubjectId(int id) throws DataBaseException;
}
