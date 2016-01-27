package com.becomejavasenior.impl;


import com.becomejavasenior.Company;
import com.becomejavasenior.GenericTemplateDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author PeterKramar
 */

public class CompanyTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Company> {

    public void create(Company object) {

    }

    public Company read(int key) {
        return null;
    }

    public void update(Company object) {

    }

    public void delete(int id) {

    }

    public List<Company> readAll() {
        return null;
    }

    public int findTotalCompanies(){
        String sql = "SELECT COUNT(*) FROM company";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

}
