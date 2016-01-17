package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */

public class CompanyServiceImpl implements CompanyService {

    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class);
    private DaoFactory dao;
    private CompanyDAOImpl companyDAO;

    public CompanyServiceImpl(){
        try {
            dao = new PostgreSqlDaoFactory();
            companyDAO = (CompanyDAOImpl)dao.getDao(Company.class);
        } catch (DataBaseException e) {
            logger.error(e);
        }
    }

    @Override
    public Company saveCompany(Company company) throws DataBaseException {
        if (company.getId() == 0) {
            return companyDAO.create(company);
        } else {
            companyDAO.update(company);
            return companyDAO.read(company.getId());
        }
    }

    @Override
    public void deleteCompany(int id) throws DataBaseException {
        companyDAO.delete(id);
    }

    @Override
    public Company findCompanyById(int id) throws DataBaseException {
        Company company = companyDAO.read(id);
        return company;
    }

    @Override
    public List<Company> findCompanies() throws DataBaseException{
        List<Company> companyList = companyDAO.readAll();
        return companyList;
    }

    @Override
    public List<Company> findCompaniesLite() throws DataBaseException{
        List<Company> companyList = companyDAO.readAllLite();
        return companyList;
    }

    @Override
    public Company findCompanyByName(String name) throws DataBaseException {
        return companyDAO.readCompanyByName(name);
    }
}
