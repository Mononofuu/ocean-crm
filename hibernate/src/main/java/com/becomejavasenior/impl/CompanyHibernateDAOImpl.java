package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class CompanyHibernateDAOImpl extends GeneralHibernateContactDAO<Company> implements CompanyDAO {
    @Override
    public Class getObjectСlass() {
        return Company.class;
    }

    @Override
    public Company readCompanyByName(String name) throws DataBaseException {
        return readContactByName(name);
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Company company = new Company();
        company.setId(id);
        delete(company);
    }
}
