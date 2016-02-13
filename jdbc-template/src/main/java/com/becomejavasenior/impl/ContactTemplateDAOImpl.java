package com.becomejavasenior.impl;


import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PeterKramar
 */


public class ContactTemplateDAOImpl extends SubjectTemplateDAOImpl<Contact> implements ContactDAO {

    @Autowired
    private RowMapper<Contact> contactRowMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TagDAO tagDAO;

    private final static Logger LOGGER = LogManager.getLogger(ContactTemplateDAOImpl.class);

    private static final String INSERT_QUERY = "INSERT INTO contact (id, post, phone_type_id, phone, email, skype, company_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String READ_ALL_QUERY =  "SELECT DISTINCT contact.id contact_id, post, phone_type_id, phone, email, skype, contact.company_id contact_company_id," +
            " name, removed  FROM contact JOIN subject ON subject.id=contact.id ";
    private static final String DELETE_QUERY = "DELETE FROM contact WHERE id = ?";

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Contact create(Contact contact) {
        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                try {
                    statement.setInt(1, createSubject(contact));
                } catch (DataBaseException e) {
                    LOGGER.error(e);
                }

                statement.setString(2, contact.getPost());
                if (contact.getPhoneType() != null) {
                    statement.setInt(3, contact.getPhoneType().ordinal() + 1);
                } else {
                    statement.setNull(3, Types.INTEGER);
                }
                statement.setString(4, contact.getPhone());
                statement.setString(5, contact.getEmail());
                statement.setString(6, contact.getSkype());
                if (contact.getCompany() != null) {
                    statement.setInt(7, contact.getCompany().getId());
                } else {
                    statement.setNull(7, Types.INTEGER);
                }
                return statement;

            }
        }, holder);
        return read((Integer)holder.getKeys().get("id"));
    }

    public Contact read(int id) {
        return getJdbcTemplate().queryForObject(READ_ALL_QUERY+"WHERE contact.id = ?", new Object[]{id}, contactRowMapper);
    }

    public void update(Contact contact) {
        String query = "UPDATE contact SET post = :post, phone_type_id = :phone_type_id, phone = :phone, email = :email," +
                "skype = :skype, company_id = :company_id WHERE id = :id";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("post", contact.getPost());
        params.put("phone_type_id", contact.getPhoneType().ordinal() + 1);
        params.put("phone", contact.getPhone());
        params.put("email", contact.getEmail());
        params.put("skype", contact.getSkype());
        params.put("company_id", contact.getCompany().getId());
        params.put("id", contact.getId());

        namedParameterJdbcTemplate.update(query, params);
    }

    public void delete(int id) {
        getJdbcTemplate().update(DELETE_QUERY, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        });
    }

    public List<Contact> readAll() {
        return getJdbcTemplate().query(READ_ALL_QUERY, contactRowMapper);
    }

    public int findTotalEntryes(){
        String sql = "SELECT COUNT(*) FROM contact";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public Contact readContactByName(String name) throws DataBaseException {
        return getJdbcTemplate().queryForObject(READ_ALL_QUERY+"WHERE name = ?", new String[]{name}, contactRowMapper);
    }

    public List<Contact> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        return null;
    }

    public Contact readLite(int id) throws DataBaseException {
        return getJdbcTemplate().queryForObject(READ_ALL_QUERY+"WHERE contact.id = ?", new Object[]{id}, contactRowMapper);
    }

    public List<Contact> readAllLite() throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY, contactRowMapper);
    }

    @Override
    public List<Tag> readAllContactsTags() throws DataBaseException {
        return tagDAO.readAll(SubjectType.CONTACT_TAG);
    }
}
