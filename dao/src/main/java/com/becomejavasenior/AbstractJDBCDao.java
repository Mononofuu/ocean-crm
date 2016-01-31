package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

//@Repository
public abstract class AbstractJDBCDao<T> implements GenericDao<T> {
    private final static Logger LOGGER = LogManager.getLogger(AbstractJDBCDao.class);

    @Autowired
    public DataSource dataSource;

    protected Connection getConnection() throws DataBaseException {
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
        try (PreparedStatement statement = getConnection().prepareStatement(getReadQuery())) {
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
        try (Connection connection = getConnection();
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
        try (PreparedStatement statement = getConnection().prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement statement = getConnection().prepareStatement(getUpdateQuery())) {
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
        try (PreparedStatement statement = getConnection().prepareStatement(getDeleteQuery())) {
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
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery())) {
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
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery())) {
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

    protected List<T> realiseQuery(String query) throws DataBaseException {
        List<T> result;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
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
