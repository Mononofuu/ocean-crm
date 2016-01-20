package com.becomejavasenior.impl;


import com.becomejavasenior.Contact;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author PeterKramar
 */

public class ContactTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Contact> {

    public void create(Contact object) {
        
    }

    public Contact read(int key) {
        return null;
    }

    public void update(Contact object) {

    }

    public void delete(int id) {

    }

    public List<Contact> readAll() {
        return null;
    }

    public int findTotalContacts(){
        String sql = "SELECT COUNT(*) FROM contact";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

}
