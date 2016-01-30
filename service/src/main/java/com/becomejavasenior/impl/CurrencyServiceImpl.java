package com.becomejavasenior.impl;

import com.becomejavasenior.Currency;
import com.becomejavasenior.CurrencyService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {
    static final Logger logger = LogManager.getRootLogger();
    @Autowired
    private CurrencyDAO currencyDAO;

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
