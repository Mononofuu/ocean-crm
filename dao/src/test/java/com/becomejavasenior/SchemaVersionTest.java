package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.*;

public class SchemaVersionTest {
    private DaoFactory daoFactory;
    private Connection connection;
    private Logger logger = LogManager.getLogger(TaskDAOImplTest.class);

    @Before
    public void SetUp() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
        connection = daoFactory.getConnection();
    }

    @Test
    public void schemaVersionTest() throws SQLException{
        String query = "SELECT version FROM db_version";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        rs.next();
        String version=rs.getString("version");
        assertEquals(SchemaVersion.getDbVersion(),version);
    }

    @After
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Connection close fail", e);
        }
    }
}
