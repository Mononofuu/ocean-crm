package com.becomejavasenior.impl;

import com.becomejavasenior.Currency;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class CurrencyTemplateDAOImpl extends JdbcDaoSupport implements CurrencyDAO {
    public Currency create(Currency object) throws DataBaseException {
        return null;
    }

    public Currency read(int key) throws DataBaseException {
        return null;
    }

    public Currency readLite(int key) throws DataBaseException {
        return null;
    }

    public void update(Currency object) throws DataBaseException {

    }

    public void delete(int id) throws DataBaseException {

    }

    public List<Currency> readAll() throws DataBaseException {
        return null;
    }

    public List<Currency> readAllLite() throws DataBaseException {
        return null;
    }
}
