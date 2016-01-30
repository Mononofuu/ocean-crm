package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Set;

@Repository
public abstract class AbstractJDBCDao<T> implements GenericDao<T>{
    private final static Logger LOGGER = LogManager.getLogger(AbstractJDBCDao.class);
    private static DaoFactory daoFactory;

    @Autowired
    @Qualifier("dataSourceHsql")
    public DataSource dataSource;

    protected AbstractJDBCDao() {
    }

    protected AbstractJDBCDao(DaoFactory daoFactory) {
        AbstractJDBCDao.daoFactory = daoFactory;
    }

    public static void setDaoFactory(DaoFactory daoFactory) {
        AbstractJDBCDao.daoFactory = daoFactory;
    }


    protected Connection getConnection()throws DataBaseException{
//        return daoFactory.getConnection();
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.catching(e);
        }
        return null;
    }

    /**
     * Возвращает sql запрос для получения всех записей.
     * SELECT * FROM [Table]
     */
    public abstract String getReadAllQuery();


    public String getReadQuery() {
        return getReadAllQuery() + getConditionStatment();
    }

    /**
     * Возвращает sql запрос для вставки новой записи в базу данных и возврата id новой записи.
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Возвращает sql запрос для обновления записи.
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    public abstract String getUpdateQuery();

    /**
     * Возвращает sql запрос для удаления записи из базы данных.
     * DELETE FROM [Table] WHERE id= ?;
     */
    public abstract String getDeleteQuery();

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws DataBaseException;

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet не заполняя их другими сущностями.
     */
    protected List<T> parseResultSetLite(ResultSet rs) throws DataBaseException {
        return parseResultSet(rs);
    }

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DataBaseException;

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DataBaseException;

    /**
     * Переопределяется для таблиц у которых нет id.
     */
    protected String getConditionStatment() {
        return " WHERE id = ?";
    }

    @Override
    public T read(int key) throws DataBaseException {
        T result;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadQuery())) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            List<T> allObjects = parseResultSet(rs);
            if (allObjects.isEmpty()) {
                return null;
            }
            result = allObjects.get(0);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public T readLite(int key) throws DataBaseException {
        T result;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadQuery())) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            List<T> allObjects = parseResultSetLite(rs);
            if (allObjects.isEmpty()) {
                return null;
            }
            result = allObjects.get(0);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public T create(T object) throws DataBaseException {
        T result;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object); // помещаем в запрос параметры object
            statement.executeUpdate();
            // получаем обратно новую запись через возвращенный id записи
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int key = rs.getInt(1);
            result = read(key);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        if (result == null) {
            throw new DataBaseException();
        }
        return result;
    }

    @Override
    public void update(T object) throws DataBaseException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getUpdateQuery())) {
            prepareStatementForUpdate(statement, object);
            int state = statement.executeUpdate();
            if (state != 1) {
                LOGGER.error("State more then one");
                throw new DataBaseException();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    @Override
    public void delete(int id) throws DataBaseException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<T> readAll() throws DataBaseException {
        List<T> result;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery())) {
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        if (result == null) {
            throw new DataBaseException();
        }
        return result;
    }

    @Override
    public List<T> readAllLite() throws DataBaseException {
        List<T> result;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery())) {
            ResultSet rs = statement.executeQuery();
            result = parseResultSetLite(rs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        if (result == null) {
            throw new DataBaseException();
        }
        return result;
    }

    /**
     * Возвращает dao для переданного класса с текущим connection и daoFactory
     */
    protected GenericDao getDaoFromCurrentFactory(Class clazz) throws DataBaseException {
        return daoFactory.getDao(clazz);
    }

    /**
     * Метод для работы с обьектами наследующими Subject
     */
    protected <T extends Subject> int createSubject(T object) throws DataBaseException {
        if (object instanceof Subject) {
            GenericDao<Subject> subjectDao = getDaoFromCurrentFactory(Subject.class);
            Subject subject = subjectDao.create(object);
            createTags(object.getTags(), subject);
            return subject.getId();
        } else {
            throw new DataBaseException();
        }
    }

    private  <T extends Subject> void createTags(Set<Tag> tags, T object) throws DataBaseException {
        if (tags == null) {
            return;
        } else {
            GenericDao<Tag> tagDao = getDaoFromCurrentFactory(Tag.class);
            GenericDao<SubjectTag> subjectTagDao = getDaoFromCurrentFactory(SubjectTag.class);
            for (Tag tag : tags) {
                SubjectTag subjectTag = new SubjectTag();
                if(object instanceof Contact){
                    tag.setSubjectType(SubjectType.CONTACT_TAG);
                }else if(object instanceof Company){
                    tag.setSubjectType(SubjectType.COMPANY_TAG);
                }else if(object instanceof Deal){
                    tag.setSubjectType(SubjectType.DEAL_TAG);
                }
                tag = tagDao.create(tag);
                subjectTag.setTag(tag);
                subjectTag.setSubject(object);
                subjectTagDao.create(subjectTag);
            }
        }
    }

    protected List<T> realiseQuery(String query)throws DataBaseException{
        List<T> result;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ){
            ResultSet rs = statement.executeQuery(query);
            result = parseResultSet(rs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(e);
        }
        if (result == null) {
            throw new DataBaseException();
        }
        return result;
    }
}
