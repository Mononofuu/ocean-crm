package com.becomejavasenior;

import com.becomejavasenior.impl.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractJDBCDaoFactory implements DaoFactory {
    private String url;
    private Properties prop = new Properties();
    private Map<Class, GenericDao> daoClasses = new HashMap<>();

    public AbstractJDBCDaoFactory() throws DataBaseException {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream(getPropertyFileName()));
            Class.forName(prop.getProperty("driver"));
            url = prop.getProperty("url");
        } catch (ClassNotFoundException | IOException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Connection getConnection() throws DataBaseException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return connection;
    }

    @Override
    public GenericDao getDao(Class clazz) throws DataBaseException {
        GenericDao result;
        if (daoClasses.size() > 0) {
            result = daoClasses.get(clazz);
        } else {
            initializeDaoClasses(getConnection());
            result = daoClasses.get(clazz);
        }
        if (result == null) {
            throw new DataBaseException("Соответствующий класс не найден");
        }
        return result;
    }

    /**
     * Возвращает имя property файла с параметрами соединения.
     */
    protected abstract String getPropertyFileName();

    private void initializeDaoClasses(Connection connection) throws DataBaseException {
        daoClasses.put(Contact.class, new ContactDAOImpl(connection));
        daoClasses.put(Subject.class, new SubjectDAOImpl(connection));
        daoClasses.put(Company.class, new CompanyDAOImpl(connection));
        daoClasses.put(PhoneType.class, new PhoneTypeDAOImpl(connection));
        daoClasses.put(SubjectTag.class, new SubjectTagDAOImpl(connection));
        daoClasses.put(Tag.class, new TagDAOImpl(connection));
        daoClasses.put(User.class, new UserDAOImpl(connection));
        daoClasses.put(Comment.class, new CommentDAOImpl(connection));
        daoClasses.put(Currency.class, new CurrencyDAOImpl(connection));
        daoClasses.put(DealContact.class, new DealContactDAOImpl(connection));
        daoClasses.put(Deal.class, new DealDAOImpl(connection));
        daoClasses.put(DealStatus.class, new DealStatusDAOImpl(connection));
        daoClasses.put(Task.class, new TaskDAOImpl(connection));
        daoClasses.put(File.class, new FileDAOImpl(connection));

    }
}