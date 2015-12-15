package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealContactDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealContactDAOImpl extends AbstractJDBCDao<DealContact> implements DealContactDAO {

    @Override
    public List<Contact> getAllContactsBySubjectId(int id) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + " WHERE deal_id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<DealContact> list = parseResultSet(rs);
            for (DealContact dealContact : list) {
                result.add(dealContact.getContact());
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM deal_contact";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO deal_contact (deal_id, contact_id) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE deal_contact SET deal_id = ?, contact_id=? WHERE deal_id = ? AND contact_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM deal_contact WHERE id= ?";
    }

    @Override
    protected List<DealContact> parseResultSet(ResultSet rs) throws DataBaseException {
        List<DealContact> result = new ArrayList<>();
        try{
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            GenericDao contactDao = getDaoFromCurrentFactory(Contact.class);
            while (rs.next()) {
                DealContact dealContact = new DealContact();
                Subject subject = (Subject) subjectDao.read(rs.getInt("deal_id"));
                dealContact.setSubject(subject);
                Contact contact = (Contact) contactDao.read(rs.getInt("contact_id"));
                dealContact.setContact(contact);
                result.add(dealContact);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, DealContact object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setInt(2, object.getContact().getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, DealContact object) throws DataBaseException {
        int subjectId = object.getSubject().getId();
        int contactId = object.getContact().getId();
        try {
            statement.setInt(1, subjectId);
            statement.setInt(2, contactId);
            statement.setInt(3, subjectId);
            statement.setInt(4, contactId);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
