package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.Currency;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */
@Repository
public class CurrencyHibernateDAOImpl extends AbstractHibernateDAO<Currency> implements CurrencyDAO{

    @Override
    public Class getObjectСlass() {
        return Currency.class;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Currency currency = new Currency();
        currency.setId(id);
        delete(currency);
    }
}
