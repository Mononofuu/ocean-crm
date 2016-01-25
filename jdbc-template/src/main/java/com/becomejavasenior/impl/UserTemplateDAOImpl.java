package com.becomejavasenior.impl;

import com.becomejavasenior.Comment;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Language;
import com.becomejavasenior.User;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import com.becomejavasenior.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */

public class UserTemplateDAOImpl extends JdbcDaoSupport implements UserDAO {

    @Autowired
    @Qualifier("dataSource")
    private DataSource myDataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(myDataSource);
    }

    public User create(final User user) {
        final String sql = "INSERT INTO users (name, login, password, photo, email, phone_mob, phone_work, language, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        getJdbcTemplate().update(sql, user.getName(), user.getLogin(), user.getPassword(), user.getPhoto(), user.getEmail(), user.getPhoneHome(), user.getPhoneWork(), user.getLanguage(), user.getComments());
        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getName());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getPassword());
                statement.setBytes(4, user.getPhoto());
                statement.setString(5, user.getEmail());
                statement.setString(6, user.getPhoneHome());
                statement.setString(7, user.getPhoneWork());
                statement.setString(8, user.getLanguage() != null ? user.getLanguage().toString() : null);
                statement.setString(9, "");
                return statement;
            }
        }, holder);
        int id = holder.getKey().intValue();
        user.setId(id);
        return user;
    }

    public User read(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return getJdbcTemplate().queryForObject(
                sql, new Object[]{id},
                new UserRowMapper());
    }

    public List<User> readAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<User>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            User user = new User();
            user.setName((String) row.get("name"));
            user.setLogin((String) row.get("login"));
            user.setPassword((String) row.get("password"));
            user.setPhoto((byte[]) row.get("photo"));
            user.setEmail((String) row.get("email"));
            user.setPhoneHome((String) row.get("phone_mob"));
            user.setPhoneWork((String) row.get("phone_work"));
            String language = (String) row.get("language");
            user.setLanguage(language != null ? Language.valueOf(language) : null);
            Comment comment = new Comment();
            comment.setText((String) row.get("comment"));
            List<Comment> comments = new ArrayList<Comment>();
            comments.add(comment);
            user.setComments(comments);
            users.add(user);
        }
        return users;
    }

    public void update(final User user) {
        String sql = "UPDATE users SET name = ?, login = ?, password = ?, photo = ?, email = ?, phone_mob = ?, phone_work = ?, language = ?, comment = ? WHERE id = ?";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setBytes(4, user.getPhoto());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, user.getPhoneHome());
                preparedStatement.setString(7, user.getPhoneWork());
                preparedStatement.setString(8, user.getLanguage().name());
                preparedStatement.setString(9, user.getComments().toString());
            }
        });
    }

    public void delete(final int id) {
        String sql = "DELETE FROM users WHERE id= ?;";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        });
    }

    public User readLite(int key) throws DataBaseException {
        return null;
    }

    public List<User> readAllLite() throws DataBaseException {
        return null;
    }
}
