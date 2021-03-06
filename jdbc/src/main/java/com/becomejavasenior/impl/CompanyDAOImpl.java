package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.SubjectDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Repository
public class CompanyDAOImpl extends GeneralContactDAOImpl<Company> implements CompanyDAO {
    private static final Logger LOGGER = LogManager.getLogger(CompanyDAOImpl.class);
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private TagDAO tagDAO;

    @Override
    public Company readCompanyByName(String name) throws DataBaseException {
        return readContactByName(name);
    }

    @Override
    protected String getConditionStatment() {
        return "WHERE company.id = ?";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT DISTINCT company.id, phone_number, email, web, address, name, removed  FROM company JOIN subject ON subject.id=company.id ";
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
                Subject subject = subjectDAO.read(rs.getInt("id"));
                company.setId(subject.getId());
                company.setUser(subject.getUser());
                company.setName(subject.getName());
                company.setPhoneNumber(rs.getString("phone_number"));
                company.setEmail(rs.getString("email"));
                company.setWeb(getUrl(rs.getString("web")));
                company.setAddress(rs.getString("address"));
                result.add(company);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    private URL getUrl(String urlString){
        URL url = null;
        if(urlString!=null&&!"".equals(urlString)){
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                LOGGER.error(e);
            }
        }
        return url;
    }

    @Override
    protected List<Company> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Company> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                company.setPhoneNumber(rs.getString("phone_number"));
                company.setEmail(rs.getString("email"));
                company.setWeb(getUrl(rs.getString("web")));
                company.setAddress(rs.getString("address"));
                result.add(company);
            }
        } catch (SQLException e) {
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
            statement.setInt(1, subjectDAO.createSubject(object));
            statement.setString(2, object.getPhoneNumber());
            statement.setString(3, object.getEmail());
            if(object.getWeb()!=null){
                statement.setString(4, object.getWeb().toString());
            }else{
                statement.setNull(4, Types.CHAR);
            }
            statement.setString(5, object.getAddress());
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
            subjectDAO.update(object);
            statement.setString(1, object.getPhoneNumber());
            statement.setString(2, object.getEmail());
            statement.setString(3, object.getWeb().toString());
            statement.setString(4, object.getAddress());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public void delete(int id) throws DataBaseException {
        subjectDAO.delete(id);
    }

    @Override
    protected String getLeftJoinTask() {
        return " LEFT JOIN task ON company.id=task.subject_id";
    }

    @Override
    protected String getLeftJoinDeal() {
        return " LEFT JOIN deal ON company.id=deal.contact_main_id";
    }

    @Override
    protected String getLeftJoinSubjectTag() {
        return " JOIN subject_tag ON company.id=subject_tag.subject_id";
    }

    @Override
    protected String getTableName() {
        return "company";
    }

    @Override
    public List<Tag> readAllCompanyesTags() throws DataBaseException {
        return tagDAO.readAll(SubjectType.COMPANY_TAG);
    }
}
