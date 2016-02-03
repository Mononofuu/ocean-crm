package com.becomejavasenior.mapper;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Grants;
import com.becomejavasenior.impl.RoleTemplateDAOImpl;
import com.becomejavasenior.impl.UserTemplateDAOImpl;
import com.becomejavasenior.interfacedao.RoleDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lybachevskiy.Vladislav
 */
@Component
public class GrantsRowMapper implements RowMapper<Grants> {
    private static final Logger LOGGER = LogManager.getLogger(GrantsRowMapper.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    public Grants mapRow(ResultSet resultSet, int i) throws SQLException {
        Grants grants = new Grants();
        try {
            grants.setUser(userDAO.read(resultSet.getInt("user_id")));
            grants.setRole(roleDAO.read(resultSet.getInt("role_id")));
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        grants.setLevel(resultSet.getInt("level"));
        return grants;
    }
}
