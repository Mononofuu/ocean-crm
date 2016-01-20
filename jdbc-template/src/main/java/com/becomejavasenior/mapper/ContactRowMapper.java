package com.becomejavasenior.mapper;

import com.becomejavasenior.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//заготовка
public class ContactRowMapper implements RowMapper<Contact> {

    public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getInt("id"));
        return contact;
    }
}
