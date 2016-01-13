package com.becomejavasenior;

import com.becomejavasenior.Company;
import com.becomejavasenior.DataBaseException;

import java.util.List;


/**
 * Created by Peter on 18.12.2015.
 */
public interface CompanyService {

        Company findCompanyById(int id) throws DataBaseException;
        void saveCompany(Company company) throws DataBaseException;
        void deleteCompany(int id) throws DataBaseException;
        List<Company> findCompanies() throws DataBaseException;
        List<Company> findCompaniesLite() throws DataBaseException;
}
