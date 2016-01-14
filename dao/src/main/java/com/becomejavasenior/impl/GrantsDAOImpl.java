package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.GrantsDAO;
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
public class GrantsDAOImpl extends AbstractJDBCDao<Grants> implements GrantsDAO {

    private final static Logger LOGGER = LogManager.getLogger(GrantsDAOImpl.class);

    public GrantsDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM grants";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO grants (user_id, role_id, level) VALUES (?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE grants SET role_id = ?, level = ? WHERE user_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    protected List<Grants> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Grants> result = new ArrayList<>();
        try {
            GenericDao userDao = getDaoFromCurrentFactory(User.class);
            GenericDao roleDao = getDaoFromCurrentFactory(Role.class);
            while (rs.next()) {
                Grants grants = new Grants();
                grants.setUser((User) userDao.read(rs.getInt("user_id")));
                Role role = (Role) roleDao.read(rs.getInt("role_id"));
                grants.setRole(role);
                grants.setLevel(rs.getInt("level"));
                result.add(grants);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Grants object) throws DataBaseException {
        try {
            statement.setInt(1, object.getRole().getId());
            statement.setInt(2, object.getLevel());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Grants object) throws DataBaseException {
        try {
            GenericDao grantsDao = getDaoFromCurrentFactory(Grants.class);
            grantsDao.update(object);
            statement.setInt(1, object.getRole().getId());
            statement.setInt(2, object.getLevel());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }
}
