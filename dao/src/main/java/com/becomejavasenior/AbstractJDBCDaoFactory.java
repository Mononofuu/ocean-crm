package com.becomejavasenior;

import com.becomejavasenior.impl.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
        } catch (NullPointerException e){
            URI dbUri = null;
            try {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            } catch (URISyntaxException e1) {
                throw new DataBaseException(e);
            }
            url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            prop.put("user", dbUri.getUserInfo().split(":")[0]);
            prop.put("password", dbUri.getUserInfo().split(":")[1]);
            prop.put("driver", "org.postgresql.Driver");
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

    }
}