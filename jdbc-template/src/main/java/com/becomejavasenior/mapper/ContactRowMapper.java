package com.becomejavasenior.mapper;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ContactRowMapper implements RowMapper<Contact> {

    private final static Logger LOGGER = LogManager.getLogger(ContactRowMapper.class);

    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private PhoneTypeDAO phoneTypeDAO;
    @Autowired
    private SubjectTagDAO subjectTagDAO;

    public Contact mapRow(ResultSet resultSet, int i) throws SQLException {

        Contact contact = new Contact();
        int id = resultSet.getInt("contact_id");
        contact.setId(id);
        contact.setName(resultSet.getString("name"));
        contact.setPost(resultSet.getString("post"));
        contact.setPhone(resultSet.getString("phone"));
        contact.setEmail(resultSet.getString("email"));
        contact.setSkype(resultSet.getString("skype"));
        contact.setRemoved(resultSet.getBoolean("removed"));
        try {
            Company company = companyDAO.read(resultSet.getInt("contact_company_id"));
            contact.setCompany(company);
            PhoneType phoneType = phoneTypeDAO.read(resultSet.getInt("phone_type_id"));
            contact.setPhoneType(phoneType);
            contact.setTags(subjectTagDAO.getAllTagsBySubjectId(id));
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        return contact;
    }
}



