package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 18.12.2015.
 */
@Service
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CompanyServiceImpl extends AbstractContactService<Company> implements CompanyService {

    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private TagDAO tagDAO;

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
    public Company saveCompany(Company company) throws DataBaseException {
        if (company.getId() == 0) {
            return companyDAO.create(company);
        } else {
            companyDAO.update(company);
            return companyDAO.read(company.getId());
        }
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataBaseException.class, readOnly = false)
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

    @Override
    public List<Company> getAllCompanyesByParameters(Map<String, String[]> parameters) throws DataBaseException {
        return getAllContactsByParameters(parameters);
    }

    @Override
    public List<Tag> getAllCompanyTags() throws DataBaseException {
        return tagDAO.readAll(SubjectType.COMPANY_TAG);
    }
}

