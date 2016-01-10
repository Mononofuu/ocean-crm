package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Company;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

public interface CompanyDAO  extends GenericDao<Company> {
    Company readCompanyByName(String name)throws DataBaseException;
}
