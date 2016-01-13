package com.becomejavasenior.impl;

import com.becomejavasenior.Company;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.CompanyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
public class CompanyServiceImpl implements CompanyService{

    static final Logger logger = LogManager.getRootLogger();
    CompanyDAO companyDao = new CompanyDAOImpl();

    @Override
    public void saveCompany(Company company) throws DataBaseException {
        if(company.getId() == 0){
            companyDao.create(company);
        }else{
            companyDao.update(company);
        }
    }

    @Override
    public void deleteCompany(int id) throws DataBaseException {
        companyDao.delete(id);
    }

    @Override
    public Company findCompanyById(int id) throws DataBaseException {
        Company company = companyDao.read(id);
        return company;
    }

    @Override
    public List<Company> findCompanies() throws DataBaseException{
        List<Company> companyList = companyDao.readAll();
        return companyList;
    }

    @Override
    public List<Company> findCompaniesLite() throws DataBaseException{
        List<Company> companyList = companyDao.readAllLite();
        return companyList;
    }

}
