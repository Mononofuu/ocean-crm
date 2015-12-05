package com.becomejavasenior.impl;

import com.becomejavasenior.*;
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
    public UserDAOImpl(  Connection connection) throws DataBaseException {
        super(connection);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (name, login, password, email, phone_mob, phone_work, language, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
//        return "INSERT INTO users (name, login, password, email, phone_mob, phone_work, language, comment, photo) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    }

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
                user.setPhoneHome(rs.getString("phone_mob"));
                user.setPhoneWork(rs.getString("phone_work"));
                switch (rs.getString("language")){
                    case "EN":
                        user.setLanguage(Language.EN);
                        break;
                    case "RU":
                        user.setLanguage(Language.RU);
                        break;
                }
//                user.setComments(rs.getString("comment"));
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
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws DataBaseException {

        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getPhoneHome());
            statement.setString(6, object.getPhoneWork());
            statement.setString(7, object.getLanguage().toString());
            statement.setString(8, "simply comment");
//            statement.setString(8, object.get"simply comment");
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
