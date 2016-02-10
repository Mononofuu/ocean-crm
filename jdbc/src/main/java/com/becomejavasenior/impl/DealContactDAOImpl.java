package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.DealContactDAO;
import com.becomejavasenior.interfacedao.DealDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Repository
public class DealContactDAOImpl extends AbstractJDBCDao<DealContact> implements DealContactDAO {

    @Autowired
    public DealDAO dealDAO;
    @Autowired
    public ContactDAO contactDAO;

    @Override
    protected String getConditionStatment() {
        return " WHERE deal_id = ?";
    }

    @Override
    public List<Contact> getAllContactsBySubjectId(int id) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + getConditionStatment())) {
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
    public void deleteDealContact(int dealId, int contactId) throws DataBaseException{
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM deal_contact WHERE deal_id = ? AND contact_id=?")) {
            statement.setInt(1, dealId);
            statement.setInt(2, contactId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
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
        return "DELETE FROM deal_contact WHERE deal_id= ?";
    }

    @Override
    protected List<DealContact> parseResultSet(ResultSet rs) throws DataBaseException {
        List<DealContact> result = new ArrayList<>();
        try{
            while (rs.next()) {
                DealContact dealContact = new DealContact();
                Deal deal = dealDAO.readLite(rs.getInt("deal_id"));
                dealContact.setDeal(deal);
                Contact contact = contactDAO.read(rs.getInt("contact_id"));
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
            statement.setInt(1, object.getDeal().getId());
            statement.setInt(2, object.getContact().getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, DealContact object) throws DataBaseException {
        int dealId = object.getDeal().getId();
        int contactId = object.getContact().getId();
        try {
            statement.setInt(1, dealId);
            statement.setInt(2, contactId);
            statement.setInt(3, dealId);
            statement.setInt(4, contactId);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
