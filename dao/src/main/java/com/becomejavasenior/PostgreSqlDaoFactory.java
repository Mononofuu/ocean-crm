package com.becomejavasenior;

public class PostgreSqlDaoFactory extends AbstractJDBCDaoFactory {

    public PostgreSqlDaoFactory() throws DataBaseException {
    }

    @Override
    protected String getPropertyFileName() {
        return "postgresql_config.properties";
    }

}
