package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DaoFactory;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.interfacedao.SubjectDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl extends AbstractJDBCDao<Subject> implements SubjectDAO {

    public SubjectDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
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
            while (rs.next()) {
                Subject subject = new Subject() {
                };
//                GenericDao userDao =  getDaoFromCurrentFactory(User.class);
//                User user = (User)userDao.read(rs.getInt("user_id"));
//                subject.setUser(user);
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
        return "INSERT INTO subject (name) VALUES (?);"; //user_id пока убран
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Subject object) throws DataBaseException {
        try {
            //statement.setInt(1,object.getUser().getId());
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE subject SET name = ? WHERE id = ?;"; //, user_id  = ?
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Subject object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            //statement.setInt(2, object.getUser().getId());
            statement.setInt(2, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
