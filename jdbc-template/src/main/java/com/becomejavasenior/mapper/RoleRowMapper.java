package com.becomejavasenior.mapper;

import com.becomejavasenior.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lybachevskiy.Vladislav
 */
public class RoleRowMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt("id"));
        role.setName(resultSet.getString("name"));
        role.setDescription(resultSet.getString("description"));
        return role;
    }
}
