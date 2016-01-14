package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Company;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

import java.util.List;

public interface CompanyDAO extends GenericDao<Company>{
    Company readCompanyByName(String name)throws DataBaseException;
    List<Company> getAllCompanyesByParameters(String userId, List<Integer> idList) throws DataBaseException;
    List<Company> getAllCompanyesWithoutTasks()throws DataBaseException;
}
