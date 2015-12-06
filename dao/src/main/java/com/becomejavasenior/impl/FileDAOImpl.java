package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.FileDAO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAOImpl extends AbstractJDBCDao<File> implements FileDAO {

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM file";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM file WHERE id= ?;";
    }

    @Override
    protected List<File> parseResultSet(ResultSet rs) throws DataBaseException {
        List<File> result = new ArrayList<>();
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            GenericDao userDao = getDaoFromCurrentFactory(User.class);
            while (rs.next()) {
                File file = new File();
                file.setId(rs.getInt("id"));
                Subject subject = (Subject) subjectDao.read(rs.getInt("subject_id"));
                file.setSubject(subject);
                file.setName(rs.getString("name"));
                file.setFileLink(new URL(rs.getString("link")));
                file.setFileFromDB(rs.getBytes("content"));
                file.setDateCreated(rs.getDate("created_date"));
                User user = (User) userDao.read(rs.getInt("user_id"));
                file.setUser(user);
                file.setSize(rs.getInt("size"));
                result.add(file);
            }
        } catch (SQLException | MalformedURLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO file (subject_id, name, link, content, created_date, user_id, size) VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, File object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setString(2, object.getName());
            statement.setString(3, object.getFileLink().toString());
            InputStream stream = new ByteArrayInputStream(object.getFileFromDB());
            statement.setBlob(4, stream, object.getFileFromDB().length);
            statement.setDate(5, new java.sql.Date(object.getDateCreated().getTime()));
            statement.setInt(6, object.getUser().getId());
            statement.setInt(7, object.getSize());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE file SET subject_id = ?, name = ?, link = ?, content = ?, created_date = ?, user_id = ?, size = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, File object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setString(2, object.getName());
            statement.setString(3, object.getFileLink().toString());
            InputStream stream = new ByteArrayInputStream(object.getFileFromDB());
            statement.setBlob(4, stream, object.getFileFromDB().length);
            statement.setDate(5, new java.sql.Date(object.getDateCreated().getTime()));
            statement.setInt(6, object.getUser().getId());
            statement.setInt(7, object.getSize());
            statement.setInt(8, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public List<File> getAllFilesBySubjectId(int id) throws DataBaseException {
        List<File> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + " WHERE subject_id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }
}
