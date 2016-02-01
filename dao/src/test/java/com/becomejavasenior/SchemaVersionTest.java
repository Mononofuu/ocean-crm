package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class SchemaVersionTest {
    private DaoFactory daoFactory;
    private static final Logger LOGGER = LogManager.getLogger(SchemaVersionTest.class);
    private static final String GET_DB_VERSION_QUERY = "SELECT version FROM db_version";
    private static final String CHANGE_DB_VERSION = "UPDATE db_version SET version = ";

    @Before
    public void setUp() throws DataBaseException {
        daoFactory = new PostgreSqlDaoFactory();
    }

    @Test
    public void schemaVersionTest() throws IOException{
        try(Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            String version=getDBVersion(statement);
            Double schemaVersion = Double.parseDouble(version);
            Double codeVersion = Double.parseDouble(SchemaVersion.getDbVersion());
            if(codeVersion>schemaVersion){
                assertEquals(true, updateSchema(connection, codeVersion, schemaVersion));
            }else {
                assertEquals(codeVersion,schemaVersion);
            }
        }catch (DataBaseException e){
            LOGGER.error(e);
        } catch (SQLException e) {
           LOGGER.error(e);
        }
    }

    @After
    public void infoMessage(){
        try(Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement()){
            LOGGER.info("DB version is "+getDBVersion(statement)+", code version is "+SchemaVersion.getDbVersion());
        } catch (SQLException e) {
            LOGGER.error(e);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
    }

    private boolean updateSchema(Connection connection, double codeVersion, double schemaVersion) throws SQLException {
        try(Statement statement = connection.createStatement()){
            Map<Double, String> files = new TreeMap<>();
            URL url = getClass().getClassLoader().getResource("sql_schema");
            File directory = new File(url.toURI());
            for(String str: directory.list()){//scaning sql_schema directory and saving x.xx_patch_description.sql files to "files" Map
                if(str.matches("\\d{1}\\.\\d{2}_.+\\.sql")){
                    Double version = Double.parseDouble(str.substring(0, 4));
                    String fileContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sql_schema/"+str), "UTF-8");
                    if(version>schemaVersion){//check if patch is
                        files.put(version, fileContent);
                    }
                }
            }
            for(Map.Entry<Double, String> entry: files.entrySet()){
                String[] queryes = entry.getValue().split(";");
                for (String query: queryes){
                    statement.addBatch(query);
                }
                int[] batchCount = statement.executeBatch();
                if(batchCount.length!=queryes.length){
                    connection.rollback();
                    LOGGER.warn("Error updating DB version");
                    return false;
                }
                statement.execute(CHANGE_DB_VERSION+entry.getKey());
                if(codeVersion==entry.getKey()){
                    connection.commit();
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("DB patch file not found", e);
            connection.rollback();
        } catch (URISyntaxException|SQLException|IOException e) {
            LOGGER.error("error updating db version", e);
            connection.rollback();
        }
        connection.rollback();
        return false;
    }

    private String getDBVersion(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery(GET_DB_VERSION_QUERY);
        rs.next();
        return rs.getString("version");
    }


}
