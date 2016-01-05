package com.becomejavasenior;

import com.becomejavasenior.impl.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractJDBCDaoFactory implements DaoFactory {
    private static BasicDataSource dataSource;
    private Map<Class, GenericDao> daoClasses = new HashMap<>();

    public AbstractJDBCDaoFactory() throws DataBaseException {
        try {
            if (dataSource == null) {
                dataSource = new BasicDataSource();
                Properties prop = new Properties();
                prop.load(getClass().getClassLoader().getResourceAsStream(getPropertyFileName()));
                dataSource.setDriverClassName(prop.getProperty("driver"));
                dataSource.setUrl(prop.getProperty("url"));
                dataSource.setUsername(prop.getProperty("user"));
                dataSource.setPassword(prop.getProperty("password"));
                dataSource.setInitialSize(10);
                dataSource.setMaxTotal(100);
                dataSource.setMaxIdle(30);
            }
        } catch (IOException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Connection getConnection() throws DataBaseException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
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
            initializeDaoClasses();
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

    private void initializeDaoClasses() throws DataBaseException {
        daoClasses.put(Contact.class, new ContactDAOImpl());
        daoClasses.put(Subject.class, new SubjectDAOImpl());
        daoClasses.put(Company.class, new CompanyDAOImpl());
        daoClasses.put(PhoneType.class, new PhoneTypeDAOImpl());
        daoClasses.put(SubjectTag.class, new SubjectTagDAOImpl());
        daoClasses.put(Tag.class, new TagDAOImpl());
        daoClasses.put(User.class, new UserDAOImpl());
        daoClasses.put(Comment.class, new CommentDAOImpl());
        daoClasses.put(Currency.class, new CurrencyDAOImpl());
        daoClasses.put(DealContact.class, new DealContactDAOImpl());
        daoClasses.put(Deal.class, new DealDAOImpl());
        daoClasses.put(DealStatus.class, new DealStatusDAOImpl());
        daoClasses.put(Task.class, new TaskDAOImpl());
        daoClasses.put(File.class, new FileDAOImpl());
        daoClasses.put(TaskType.class, new TaskTypeDAOImpl());
        daoClasses.put(Filter.class, new FilterDAOImpl());
        daoClasses.put(Grants.class, new GrantsDAOImpl());
        daoClasses.put(Role.class, new RoleDAOImpl());
        daoClasses.put(Event.class, new EventDAOImpl());

    }
}