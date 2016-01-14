package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.SubjectDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl extends AbstractJDBCDao<Subject> implements SubjectDAO {

    public SubjectDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM subject WHERE id= ?;";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM subject";
    }

    @Override
    protected List<Subject> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Subject> result = new ArrayList<>();
        try {
            GenericDao userDao =  getDaoFromCurrentFactory(User.class);
            while (rs.next()) {
                Subject subject = new Subject() {};
                User user = (User)userDao.read(rs.getInt("content_owner_id"));
                subject.setUser(user);
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                result.add(subject);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected List<Subject> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Subject> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Subject subject = new Subject() {
                };
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                result.add(subject);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO subject (content_owner_id, name) VALUES (?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Subject object) throws DataBaseException {
        try {
            if(object.getUser()!=null){
                statement.setInt(1,object.getUser().getId());
            }else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE subject SET name = ?, content_owner_id = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Subject object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            if(object.getUser()!=null){
                statement.setInt(2, object.getUser().getId());
            }else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException();
        }

    }
}
