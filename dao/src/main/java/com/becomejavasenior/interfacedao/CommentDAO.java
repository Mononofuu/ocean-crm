package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Comment;
import com.becomejavasenior.DataBaseException;

import java.util.List;

public interface CommentDAO {

    public List<Comment> getAllCommentsBySubjectId(int id) throws DataBaseException;
}
