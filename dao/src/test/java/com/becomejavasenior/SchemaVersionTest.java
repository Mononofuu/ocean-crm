package com.becomejavasenior;

import com.becomejavasenior.config.DAODataSourceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAODataSourceConfig.class})
public class SchemaVersionTest {
    private static final Logger LOGGER = LogManager.getLogger(TaskDAOImplTest.class);

    @Autowired
    DataSource dataSource;

    @Test
    public void schemaVersionTest() throws SQLException {
        String query = "SELECT version FROM db_version";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            rs.next();
            String version = rs.getString("version");
            assertEquals(SchemaVersion.getDbVersion(), version);
        }
    }
}
