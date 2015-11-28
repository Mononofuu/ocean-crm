package com.becomejavasenior.impl;


import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl extends AbstractJDBCDao<Contact> implements ContactDAO {

    public ContactDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM contact";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM contact WHERE id= ?;";
    }

    @Override
    protected List<Contact> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Contact> result = new ArrayList<>();
        GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
        GenericDao phoneTypeDao = getDaoFromCurrentFactory(PhoneType.class);
        GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
        SubjectTagDAOImpl subjectTagDAOImpl = (SubjectTagDAOImpl) getDaoFromCurrentFactory(SubjectTag.class);
        try {
            while (rs.next()) {
                Contact contact = new Contact();
                int id = rs.getInt("id");
                // Считываем данные из таблицы subject
                Subject subject = (Subject) subjectDao.read(id);
                contact.setId(id);
                contact.setName(subject.getName());
                contact.setPost(rs.getString("post"));
                // Считываем данные из таблицы phone_type
                PhoneType phoneType = (PhoneType) phoneTypeDao.read(rs.getInt("phone_type_id"));
                contact.setPhoneType(phoneType);
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSkype(rs.getString("skype"));
                // Считываем данные из таблицы company
                Company company = (Company) companyDao.read(rs.getInt("company_id"));
                contact.setCompany(company);
                // Считываем тэги
                contact.setTags(subjectTagDAOImpl.getAllTagsBySubjectId(id));
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
            statement.setInt(1, createSubject(object));
            statement.setString(2, object.getPost());
            statement.setInt(3, object.getPhoneType().ordinal() + 1);
            statement.setString(4, object.getPhone());
            statement.setString(5, object.getEmail());
            statement.setString(6, object.getSkype());
            statement.setInt(7, object.getCompany().getId());
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
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
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
        GenericDao<Subject> subjectDao = getDaoFromCurrentFactory(Subject.class);
        subjectDao.delete(id);
    }
}
