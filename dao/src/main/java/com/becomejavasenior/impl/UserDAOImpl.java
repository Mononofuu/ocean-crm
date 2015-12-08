package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DaoFactory;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.User;
import com.becomejavasenior.interfacedao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends AbstractJDBCDao<User> implements UserDAO {

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id= ?;";
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DataBaseException {
        List<User> result = new ArrayList<>();
        try{
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setPhoto(rs.getBytes("photo"));
                user.setEmail(rs.getString("email"));
                user.setPhoneWork(rs.getString("phone_mob"));
                user.setPhoneHome(rs.getString("phone_work"));

                result.add(user);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM users";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (name) VALUES (?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws DataBaseException {

    }
}
