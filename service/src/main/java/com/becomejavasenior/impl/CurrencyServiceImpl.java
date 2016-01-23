package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class CurrencyServiceImpl implements CurrencyService {
    static final Logger logger = LogManager.getRootLogger();
    DaoFactory daoFactory;
    CurrencyDAO currencyDAO;

    public CurrencyServiceImpl() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
        currencyDAO = (CurrencyDAO) daoFactory.getDao(Currency.class);
    }

    @Override
    public Currency findCurrencyById(int id) throws DataBaseException {
        return currencyDAO.read(id);
    }

    @Override
    public Currency saveCurrency(Currency currency) throws DataBaseException {
        if (currency.getId() == 0) {
            currency = currencyDAO.create(currency);
        } else {
            currencyDAO.update(currency);
            currency = currencyDAO.read(currency.getId());
        }
        return currency;
    }

    @Override
    public void deleteCurrency(int id) throws DataBaseException {
        currencyDAO.delete(id);
    }

    @Override
    public List<Currency> findCurrencies() throws DataBaseException {
        return currencyDAO.readAll();
    }
}
