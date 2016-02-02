package com.becomejavasenior.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Configuration
@ComponentScan(basePackages = "com.becomejavasenior.impl")
public class DAODataSourceConfig {
    private final static Logger LOGGER = LogManager.getLogger(DAODataSourceConfig.class);
    private static final String PROPERTY_FILE_NAME = "postgresql_config.properties";

    @Bean
    @Profile("default")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
            dataSource.setDriverClassName(prop.getProperty("driver"));
            dataSource.setUrl(prop.getProperty("url"));
            dataSource.setUsername(prop.getProperty("user"));
            dataSource.setPassword(prop.getProperty("password"));
            dataSource.setInitialSize(20);
            dataSource.setMaxTotal(60);
            dataSource.setMaxIdle(30);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (NullPointerException e) {
            LOGGER.warn("property file not found, trying to connect by getting system variable", e);
            URI dbUri = null;
            try {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            } catch (URISyntaxException e1) {
                LOGGER.error(e1);
            }
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
            dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
            dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
            dataSource.setInitialSize(10);
            dataSource.setMaxTotal(500);
            dataSource.setMaxIdle(30);
        }
        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource getEmbeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema_hsql.sql")
                .addScript("data_hsql.sql")
                .build();
    }
}
