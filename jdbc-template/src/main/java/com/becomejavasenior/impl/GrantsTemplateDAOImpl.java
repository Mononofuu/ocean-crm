package com.becomejavasenior.impl;

import com.becomejavasenior.Grants;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */

public class GrantsTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Grants> {

    private DataSource myDataSource;

    public GrantsTemplateDAOImpl(DataSource dataSource) {
        myDataSource = dataSource;
        setDataSource(dataSource);
    }

    public void create(Grants grants) {
        String sql = "INSERT INTO grants " +
                "(user_id, role_id, level) VALUES (?, ?, ?)";

        getJdbcTemplate().update(sql, grants.getUser().getId(),
                grants.getRole().getId(), grants.getLevel());
    }

    public Grants read(int userId) {
        String sql = "SELECT * FROM grants WHERE user_id = ?";
        return getJdbcTemplate().queryForObject(
                sql, new Object[]{userId},
                new RowMapper<Grants>() {
                    public Grants mapRow(ResultSet resultSet, int i) throws SQLException {
                        Grants grants = new Grants();
                        grants.setUser(new UserTemplateDAOImpl(myDataSource).read(resultSet.getInt("user_id")));
                        grants.setRole(new RoleTemplateDAOImpl(myDataSource).read(resultSet.getInt("role_id")));
                        grants.setLevel(resultSet.getInt("level"));
                        return grants;
                    }
                });
    }

    public List<Grants> readAll() {
        String sql = "SELECT * FROM grants";
        List<Grants> grantsList = new ArrayList<Grants>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            Grants grants = new Grants();
            grants.setUser(new UserTemplateDAOImpl(myDataSource).read((Integer) row.get("user_id")));
            grants.setRole(new RoleTemplateDAOImpl(myDataSource).read((Integer) row.get("role_id")));
            grants.setLevel((Integer) row.get("level"));
            grantsList.add(grants);
        }
        return grantsList;
    }

    public void update(final Grants grants) {
        String sql = "UPDATE grants SET role_id=?, level=? WHERE user_id=?";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, grants.getRole().getId());
                preparedStatement.setInt(2, grants.getLevel());
                preparedStatement.setInt(3, grants.getUser().getId());
            }
        });
    }

    public void delete(final int userId) {
        String sql = "DELETE FROM grants WHERE user_id= ?;";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, userId);
            }
        });
    }

}
