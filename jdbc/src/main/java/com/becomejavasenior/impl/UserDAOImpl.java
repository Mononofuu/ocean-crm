package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Repository
public class UserDAOImpl extends AbstractJDBCDao<User> implements UserDAO {

    private final static Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id= ?;";
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DataBaseException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setPhoto(rs.getBytes("photo"));
                user.setEmail(rs.getString("email"));
                user.setPhoneWork(rs.getString("phone_mob"));
                user.setPhoneHome(rs.getString("phone_work"));
                String language = rs.getString("language");
                user.setLanguage(language != null ? Language.valueOf(rs.getString("language")) : null);
                Comment comment = new Comment();
                comment.setText(rs.getString("comment"));
                List<Comment> comments = new ArrayList<>();
                comments.add(comment);
                user.setComments(comments);
                result.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
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
        return "INSERT INTO users (name, login, password, photo, email, phone_mob, phone_work, language, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setBytes(4, object.getPhoto());
            statement.setString(5, object.getEmail());
            statement.setString(6, object.getPhoneHome());
            statement.setString(7, object.getPhoneWork());
            statement.setString(8, object.getLanguage() != null ? object.getLanguage().toString() : null);
//            statement.setString(9, object.getComments().toString());
            statement.setString(9, "");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET name = ?, login = ?, password = ?, photo = ?, email = ?, phone_mob = ?, phone_work = ?, language = ?, comment = ? WHERE id = ?";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws DataBaseException {
        try {
            update(object);
            statement.setString(1, object.getName());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setBytes(4, object.getPhoto());
            statement.setString(5, object.getEmail());
            statement.setString(6, object.getPhoneHome());
            statement.setString(7, object.getPhoneWork());
            statement.setString(8, object.getLanguage().name());
            statement.setString(9, object.getComments().toString());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws DataBaseException {
        List<User> list = realiseQuery(getReadAllQuery()+" WHERE login = '"+login+"'");
        if(!list.isEmpty()){
            return list.get(0);
        }else {
            return null;
        }
    }
}
