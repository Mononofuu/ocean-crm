package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class CompanyHibernateDAO extends AbstractHibernateDAO<Company> implements CompanyDAO {
    @Override
    public Class getObjectСlass() {
        return Company.class;
    }

    @Override
    public Company readCompanyByName(String name) throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Contact.class);
        criteria.add(Restrictions.eq("name", name));
        return (Company) criteria.uniqueResult();
    }

    @Override
    public List<Company> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException {
        return null;
    }

    @Override
    public int findTotalEntryes() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Company.class);
        criteria.setProjection(Projections.rowCount());
        return (int)criteria.uniqueResult();
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Company company = new Company();
        company.setId(id);
        delete(company);
    }
}
