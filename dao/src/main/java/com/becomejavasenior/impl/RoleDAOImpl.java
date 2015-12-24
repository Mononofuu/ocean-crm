package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.RoleDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
public class RoleDAOImpl extends AbstractJDBCDao<Role> implements RoleDAO {

    private final static Logger LOGGER = LogManager.getLogger(GrantsDAOImpl.class);

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM role";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO role (name, description) VALUES(?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE role SET name=?, description=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Role> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role();
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                result.add(role);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Role object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws DataBaseException {
        try {
            GenericDao roleDao = getDaoFromCurrentFactory(Role.class);
            roleDao.update(object);
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }
}
