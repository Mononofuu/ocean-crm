package com.becomejavasenior;

import java.util.List;
import java.util.Map;


/**
 * Created by Peter on 18.12.2015.
 */
public interface CompanyService {

        Company findCompanyById(int id) throws DataBaseException;
        Company saveCompany(Company company) throws DataBaseException;
        void deleteCompany(int id) throws DataBaseException;
        List<Company> findCompanies() throws DataBaseException;
        List<Company> findCompaniesLite() throws DataBaseException;
        Company findCompanyByName(String name) throws DataBaseException;
        List<Company> getAllCompanyesByParameters(Map<String, String[]> parameters) throws DataBaseException;
}
