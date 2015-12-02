package com.becomejavasenior;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.*;

public class SchemaVersionTest {
    private DaoFactory daoFactory;
    private Connection connection;

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
}
