package com.becomejavasenior;

import com.becomejavasenior.impl.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractJDBCDaoFactory implements DaoFactory {
    private static BasicDataSource dataSource;
    private Map<Class, GenericDao> daoClasses = new HashMap<>();
    private final static Logger LOGGER = LogManager.getLogger(AbstractJDBCDaoFactory.class);

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
        } catch (NullPointerException e){
            LOGGER.warn("property file not found, trying to connect by getting system variable", e);
            URI dbUri = null;
            try {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            } catch (URISyntaxException e1) {
                throw new DataBaseException(e1);
            }
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
            dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
            dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
            dataSource.setInitialSize(10);
            dataSource.setMaxTotal(100);
            dataSource.setMaxIdle(30);
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
            throw new DataBaseException("Class not found");
        }
        return result;
    }

    /**
     * Возвращает имя property файла с параметрами соединения.
     */
    protected abstract String getPropertyFileName();

    private void initializeDaoClasses() throws DataBaseException {
        daoClasses.put(Contact.class, new ContactDAOImpl(this));
        daoClasses.put(Subject.class, new SubjectDAOImpl(this));
        daoClasses.put(Company.class, new CompanyDAOImpl(this));
        daoClasses.put(PhoneType.class, new PhoneTypeDAOImpl(this));
        daoClasses.put(SubjectTag.class, new SubjectTagDAOImpl(this));
        daoClasses.put(Tag.class, new TagDAOImpl(this));
        daoClasses.put(User.class, new UserDAOImpl(this));
        daoClasses.put(Comment.class, new CommentDAOImpl(this));
        daoClasses.put(Currency.class, new CurrencyDAOImpl(this));
        daoClasses.put(DealContact.class, new DealContactDAOImpl(this));
        daoClasses.put(Deal.class, new DealDAOImpl(this));
        daoClasses.put(DealStatus.class, new DealStatusDAOImpl(this));
        daoClasses.put(Task.class, new TaskDAOImpl(this));
        daoClasses.put(File.class, new FileDAOImpl(this));
        daoClasses.put(TaskType.class, new TaskTypeDAOImpl(this));
        daoClasses.put(Filter.class, new FilterDAOImpl(this));
        daoClasses.put(Grants.class, new GrantsDAOImpl(this));
        daoClasses.put(Role.class, new RoleDAOImpl(this));
        daoClasses.put(Event.class, new EventDAOImpl(this));

    }
}