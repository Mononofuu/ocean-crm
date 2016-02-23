package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.Company;
import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.DealDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */
@Repository
public class CompanyHibernateDAOImpl extends AbstractHibernateDAO<Company> implements CompanyDAO{


    @Override
    public Class getObjectСlass() {
        return Company.class;
    }

    @Override
    public Company readCompanyByName(String name) throws DataBaseException {
        return null;
    }

    @Override
    public List<Company> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        return null;
    }

    @Override
    public int findTotalEntryes() throws DataBaseException {
        return 0;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Company company = new Company();
        company.setId(id);
        delete(company);
    }

}

