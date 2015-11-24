package com.becomejavasenior;

import java.sql.*;
import java.util.List;
import java.util.Set;

public abstract class AbstractJDBCDao<T> implements GenericDao<T> {
    private Connection connection;
    private DaoFactory daoFactory;

    public AbstractJDBCDao(DaoFactory daoFactory, Connection connection) throws DataBaseException {
        this.connection = connection;
        this.daoFactory = daoFactory;
    }

    protected Connection getConnection() {
        return connection;
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
        try (PreparedStatement statement = connection.prepareStatement(getReadQuery())) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            List<T> allObjects = parseResultSet(rs);
            if (allObjects.size() == 0) {
                return null;
            }
            result = allObjects.get(0);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public T create(T object) throws DataBaseException {
        T result;
        try (PreparedStatement statement = connection.prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement statement = connection.prepareStatement(getUpdateQuery())) {
            prepareStatementForUpdate(statement, object);
            int state = statement.executeUpdate();
            if (state != 1) {
                throw new DataBaseException();
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public void delete(int id) throws DataBaseException {
        try (PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<T> readAll() throws DataBaseException {
        List<T> result;
        try (PreparedStatement statement = connection.prepareStatement(getReadAllQuery())) {
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
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
        return daoFactory.getDao(connection, clazz);
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
                tag = tagDao.create(tag);
                subjectTag.setTag(tag);
                subjectTag.setSubject(object);
                subjectTagDao.create(subjectTag);
            }
        }
    }
}
