package com.becomejavasenior;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface CurrencyService {
    Currency findCurrencyById(int id) throws DataBaseException;

    Currency saveCurrency(Currency currency) throws DataBaseException;

    void deleteCurrency(int id) throws DataBaseException;

    List<Currency> findCurrencies() throws DataBaseException;
}
