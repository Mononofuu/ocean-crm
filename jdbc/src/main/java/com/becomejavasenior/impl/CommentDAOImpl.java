package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CommentDAO;
import com.becomejavasenior.interfacedao.SubjectDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDAOImpl extends AbstractJDBCDao<Comment> implements CommentDAO {

    @Autowired
    public SubjectDAO subjectDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM comment";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM comment WHERE id= ?;";
    }

    @Override
    protected List<Comment> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Comment> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                Subject subject = subjectDAO.read(rs.getInt("subject_id"));
                comment.setSubject(subject);
                User user = userDAO.read(rs.getInt("user_id"));
                comment.setUser(user);
                comment.setText(rs.getString("comment"));
                comment.setDateCreated(rs.getTimestamp("created_date"));
                result.add(comment);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO comment (subject_id, comment, created_date, user_id) VALUES (?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Comment object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setString(2, object.getText());
            statement.setTimestamp(3, new java.sql.Timestamp(object.getDateCreated().getTime()));
            statement.setInt(4, object.getUser().getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE comment SET subject_id = ?, comment = ?, created_date = ?, user_id = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Comment object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setString(2, object.getText());
            statement.setTimestamp(3, new java.sql.Timestamp(object.getDateCreated().getTime()));
            statement.setInt(4, object.getUser().getId());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public List<Comment> getAllCommentsBySubjectId(int id) throws DataBaseException {
        List<Comment> result;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + " WHERE subject_id = ? ORDER BY created_date")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }
}
