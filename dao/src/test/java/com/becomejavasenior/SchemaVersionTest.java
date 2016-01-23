package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class SchemaVersionTest {
    private DaoFactory daoFactory;
    private static final Logger LOGGER = LogManager.getLogger(TaskDAOImplTest.class);

    @Before
    public void setUp() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
    }

    @Test
    public void schemaVersionTest() throws SQLException{
        String query = "SELECT version FROM db_version";
        try(Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            rs.next();
            String version=rs.getString("version");
            assertEquals(SchemaVersion.getDbVersion(),version);
        }catch (DataBaseException e){
            LOGGER.error(e);
        }
    }
}
