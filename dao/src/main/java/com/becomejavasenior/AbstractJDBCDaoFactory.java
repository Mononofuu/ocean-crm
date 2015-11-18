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
    private Map<Class, GenericDao> daoclasses = new HashMap<>();

    public AbstractJDBCDaoFactory() throws DataBaseException {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream(getPropertyFileName()));
            Class.forName(prop.getProperty("driver"));
            url = prop.getProperty("url");
        } catch (ClassNotFoundException e) {
            throw new DataBaseException(e);
        } catch (IOException e) {
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
    public GenericDao getDao(Connection connection, Class clazz) throws DataBaseException {
        GenericDao result;
        if (daoclasses.size() > 0) {
            result = daoclasses.get(clazz);
        } else {
            initializeDaoClasses(connection);
            result = daoclasses.get(clazz);
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
        daoclasses.put(Contact.class, new ContactDAOImpl(this, connection));
        daoclasses.put(Subject.class, new SubjectDAOImpl(this, connection));
        daoclasses.put(Company.class, new CompanyDAOImpl(this, connection));
        daoclasses.put(PhoneType.class, new PhoneTypeDAOImpl(this, connection));
        daoclasses.put(SubjectTag.class, new SubjectTagDAOImpl(this, connection));
        daoclasses.put(Tag.class, new TagDAOImpl(this, connection));
        daoclasses.put(User.class, new UserDAOImpl(this, connection));
        daoclasses.put(Comment.class, new CommentDAOImpl(this, connection));
    }
}