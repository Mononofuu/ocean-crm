package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Company;
import com.becomejavasenior.DataBaseException;

public interface CompanyDAO extends GeneralContactDAO<Company>{
    Company readCompanyByName(String name) throws DataBaseException;
}
