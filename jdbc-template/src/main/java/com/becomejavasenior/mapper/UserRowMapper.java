package com.becomejavasenior.mapper;

import com.becomejavasenior.Comment;
import com.becomejavasenior.Language;
import com.becomejavasenior.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
@Component
public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setPhoto(resultSet.getBytes("photo"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneHome(resultSet.getString("phone_mob"));
        user.setPhoneWork(resultSet.getString("phone_work"));
        String language = resultSet.getString("language");
        user.setLanguage(language != null ? Language.valueOf(language) : null);
        Comment comment = new Comment();
        comment.setText(resultSet.getString("comment"));
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);
        user.setComments(comments);
        return user;
    }
}
