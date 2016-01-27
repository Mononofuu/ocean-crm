package com.becomejavasenior.impl;

import com.becomejavasenior.Role;
import com.becomejavasenior.GenericTemplateDAO;
import com.becomejavasenior.mapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */

public class RoleTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Role> {

    @Autowired
    @Qualifier("dataSource")
    private DataSource myDataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(myDataSource);
    }

    public void create(Role role) {
        String sql = "INSERT INTO role (name, description) VALUES (?, ?)";
        getJdbcTemplate().update(sql, role.getName(),
                role.getDescription());
    }

    public Role read(int id) {
        String sql = "SELECT * FROM role WHERE id = ?";
        return getJdbcTemplate().queryForObject(
                sql, new Object[]{id},
                new RoleRowMapper());
    }

    public List<Role> readAll() {
        String sql = "SELECT * FROM role";
        List<Role> roles = new ArrayList<Role>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            Role role = new Role();
            role.setId((Integer) row.get("id"));
            role.setName((String) row.get("name"));
            role.setDescription((String) row.get("description"));
            roles.add(role);
        }
        return roles;
    }

    public void update(final Role role) {
        String sql = "UPDATE role SET name=?, description=? WHERE id=?";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, role.getName());
                preparedStatement.setString(2, role.getDescription());
                preparedStatement.setInt(3, role.getId());
            }
        });
    }

    public void delete(final int id) {
        String sql = "DELETE FROM role WHERE id= ?;";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        });
    }

}
