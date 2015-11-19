package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl extends AbstractJDBCDao<Company> implements CompanyDAO {
    public CompanyDAOImpl(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        super(daoFactory, connection);
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM company";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM company WHERE id= ?;";
    }

    @Override
    protected List<Company> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Company> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Company company = new Company();
                GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
                Subject subject = (Subject) subjectDao.read(rs.getInt("id"));
                company.setId(subject.getId());
                company.setUser(subject.getUser());
                company.setName(subject.getName());
                company.setPhoneNumber(rs.getString("phone_number"));
                company.setEmail(rs.getString("email"));
                company.setWeb(new URL(rs.getString("web")));
                company.setAdress(rs.getString("address"));
                result.add(company);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        } catch (MalformedURLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO company (id, phone_number, email, web, address) VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Company object) throws DataBaseException {
        try {
            statement.setInt(1, createSubject(object));
            statement.setString(2, object.getPhoneNumber());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getWeb().toString());
            statement.setString(5, object.getAdress());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE company SET phone_number = ?, email = ?, web = ?, address = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Company object) throws DataBaseException {
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
            statement.setString(1, object.getPhoneNumber());
            statement.setString(2, object.getEmail());
            statement.setString(3, object.getWeb().toString());
            statement.setString(4, object.getAdress());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
