package com.becomejavasenior;

import com.becomejavasenior.impl.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public abstract class AbstractJDBCDaoFactory implements DaoFactory {
    private final static Logger LOGGER = LogManager.getLogger(AbstractJDBCDaoFactory.class);
    @Autowired
    private BasicDataSource dataSource;
    private Map<Class, GenericDao> daoClasses = new HashMap<>();

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