package com.becomejavasenior.mapper;

import com.becomejavasenior.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//заготовка
public class CompanyRowMapper implements RowMapper<Company> {

    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getInt("id"));
        return company;
    }
}
