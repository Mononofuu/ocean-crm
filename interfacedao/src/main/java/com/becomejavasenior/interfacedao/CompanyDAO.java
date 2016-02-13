package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Company;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Tag;

import java.util.List;

public interface CompanyDAO extends GeneralContactDAO<Company>{
    Company readCompanyByName(String name) throws DataBaseException;
    List<Tag> readAllCompanyesTags() throws DataBaseException;
}
