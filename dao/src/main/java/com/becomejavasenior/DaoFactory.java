package com.becomejavasenior;

import java.sql.Connection;

public interface DaoFactory {
    Connection getConnection() throws DataBaseException;

    GenericDao getDao(Connection connection, Class clazz) throws DataBaseException;
}
