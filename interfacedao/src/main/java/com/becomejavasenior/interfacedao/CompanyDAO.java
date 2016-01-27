package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Company;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

import java.util.Date;
import java.util.List;

public interface CompanyDAO extends GenericDao<Company>{
    Company readCompanyByName(String name)throws DataBaseException;
    List<Company> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException;
}
