package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Company;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

import java.util.Date;
import java.util.List;

public interface CompanyDAO extends GenericDao<Company>{
    Company readCompanyByName(String name)throws DataBaseException;
    List<Company> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, Date taskStartDate, Date taskDueDate) throws DataBaseException;
}
