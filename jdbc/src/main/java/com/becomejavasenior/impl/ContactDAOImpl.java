package com.becomejavasenior.impl;


import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Repository
public class ContactDAOImpl extends AbstractContactDAO<Contact> implements ContactDAO {
    @Autowired
    public SubjectDAO subjectDAO;
    @Autowired
    public PhoneTypeDAO phoneTypeDAO;
    @Autowired
    public CompanyDAO companyDAO;
    @Autowired
    public SubjectTagDAO subjectTagDAO;


    @Override
    protected String getConditionStatment() {
        return "WHERE contact.id = ?";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT DISTINCT contact.id, post, phone_type_id, phone, email, skype, contact.company_id, name, removed  FROM contact JOIN subject ON subject.id=contact.id ";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM contact WHERE id= ?;";
    }

    @Override
    protected List<Contact> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Contact contact = new Contact();
                int id = rs.getInt("id");
                // Считываем данные из таблицы subject
                Subject subject = subjectDAO.read(id);
                contact.setId(id);
                contact.setName(subject.getName());
                contact.setPost(rs.getString("post"));
                // Считываем данные из таблицы phone_type
                PhoneType phoneType = phoneTypeDAO.read(rs.getInt("phone_type_id"));
                contact.setPhoneType(phoneType);
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSkype(rs.getString("skype"));
                // Считываем данные из таблицы company
                Company company = companyDAO.read(rs.getInt("company_id"));
                contact.setCompany(company);
                // Считываем тэги
                contact.setTags(subjectTagDAO.getAllTagsBySubjectId(id));
                result.add(contact);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected List<Contact> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Contact contact = new Contact();
                int id = rs.getInt("id");
                contact.setId(id);
                contact.setName(rs.getString("name"));
                contact.setPost(rs.getString("post"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSkype(rs.getString("skype"));
                result.add(contact);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO contact (id, post, phone_type_id, phone, email, skype, company_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Contact object) throws DataBaseException {
        try {
            statement.setInt(1, subjectDAO.createSubject(object));
            statement.setString(2, object.getPost());
            if (object.getPhoneType() != null) {
                statement.setInt(3, object.getPhoneType().ordinal() + 1);
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setString(4, object.getPhone());
            statement.setString(5, object.getEmail());
            statement.setString(6, object.getSkype());
            if (object.getCompany() != null) {
                statement.setInt(7, object.getCompany().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE contact SET post = ?, phone_type_id = ?, phone = ?, email = ?, skype = ?, company_id = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Contact object) throws DataBaseException {
        try {
            subjectDAO.update(object);
            statement.setString(1, object.getPost());
            statement.setInt(2, object.getPhoneType().ordinal() + 1);
            statement.setString(3, object.getPhone());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getSkype());
            statement.setInt(6, object.getCompany().getId());
            statement.setInt(7, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public void delete(int id) throws DataBaseException {
        subjectDAO.delete(id);
    }

    @Override
    public Contact readContactByName(String name) throws DataBaseException {
        Contact result;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + " WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            List<Contact> allObjects = parseResultSetLite(rs);
            if (allObjects.isEmpty()) {
                return null;
            }
            result = allObjects.get(0);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected String getLeftJoinTask() {
        return " LEFT JOIN task ON contact.id=task.subject_id";
    }

    @Override
    protected String getLeftJoinDeal() {
        return " LEFT JOIN deal ON contact.id=deal.contact_main_id";
    }

    @Override
    protected String getLeftJoinSubjectTag() {
        return " JOIN subject_tag ON contact.id=subject_tag.subject_id";
    }

    @Override
    protected String getTableName() {
        return "contact";
    }
}
