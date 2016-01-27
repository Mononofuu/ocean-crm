package com.becomejavasenior;

import java.sql.Connection;

public interface DaoFactory {
    Connection getConnection() throws DataBaseException;

    GenericDao getDao(Class clazz) throws DataBaseException;
}
