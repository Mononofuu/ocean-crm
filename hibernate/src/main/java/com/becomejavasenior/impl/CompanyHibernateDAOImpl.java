package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class CompanyHibernateDAOImpl extends GeneralHibernateContactDAO<Company> implements CompanyDAO {
    @Override
    public Class getObjectСlass() {
        return Company.class;
    }

    @Override
    public Company readCompanyByName(String name) throws DataBaseException {
        return readContactByName(name);
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Company company = new Company();
        company.setId(id);
        delete(company);
    }

    @Override
    public List<Tag> readAllCompanyesTags() throws DataBaseException {
        Criteria criteria = getCurrentSession().createCriteria(Tag.class);
        criteria.add(Restrictions.eq("subjectType", SubjectType.COMPANY_TAG));
        return criteria.list();
    }
}
