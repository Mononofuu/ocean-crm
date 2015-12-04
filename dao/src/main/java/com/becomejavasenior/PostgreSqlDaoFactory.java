package com.becomejavasenior;

public class PostgreSqlDaoFactory extends AbstractJDBCDaoFactory {

    public PostgreSqlDaoFactory() throws DataBaseException {
        AbstractJDBCDao.setDaoFactory(this);
    }

    @Override
    protected String getPropertyFileName() {
        return "postgresql_config.properties";
    }

}
