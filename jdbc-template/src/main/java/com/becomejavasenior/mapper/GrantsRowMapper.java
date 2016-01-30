package com.becomejavasenior.mapper;

import com.becomejavasenior.Grants;
import com.becomejavasenior.impl.RoleTemplateDAOImpl;
import com.becomejavasenior.impl.UserTemplateDAOImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lybachevskiy.Vladislav
 */
//@Component
public class GrantsRowMapper implements RowMapper<Grants> {

//    @Autowired
//    @Qualifier("userDao")
    private UserTemplateDAOImpl myUserDao;

//    @Autowired
//    @Qualifier("roleDao")
    private RoleTemplateDAOImpl myRoleDao;

    public Grants mapRow(ResultSet resultSet, int i) throws SQLException {
        Grants grants = new Grants();
        grants.setUser(myUserDao.read(resultSet.getInt("user_id")));
        grants.setRole(myRoleDao.read(resultSet.getInt("role_id")));
        grants.setLevel(resultSet.getInt("level"));
        return grants;
    }
}
