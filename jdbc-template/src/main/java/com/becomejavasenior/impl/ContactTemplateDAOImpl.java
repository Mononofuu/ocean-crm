package com.becomejavasenior.impl;


import com.becomejavasenior.Contact;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.Date;
import java.util.List;

/**
 * @author PeterKramar
 */

public class ContactTemplateDAOImpl extends JdbcDaoSupport implements ContactDAO {

    public Contact create(Contact object) {
        return null;
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

    public Contact readContactByName(String name) throws DataBaseException {
        return null;
    }

    public List<Contact> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        return null;
    }

    public Contact readLite(int key) throws DataBaseException {
        return null;
    }

    public List<Contact> readAllLite() throws DataBaseException {
        return null;
    }
}
