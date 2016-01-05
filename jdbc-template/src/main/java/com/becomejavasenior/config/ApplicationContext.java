package com.becomejavasenior.config;

import com.becomejavasenior.impl.GrantsTemplateDAOImpl;
import com.becomejavasenior.impl.RoleTemplateDAOImpl;
import com.becomejavasenior.impl.UserTemplateDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Lybachevskiy.Vladislav
 */
@Configuration
@PropertySource("classpath:postgresql_config.properties")
public class ApplicationContext {

    @Autowired
    Environment myEnvironment;

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(myEnvironment.getRequiredProperty("driver"));
        dataSource.setUsername(myEnvironment.getRequiredProperty("user"));
        dataSource.setUrl(myEnvironment.getRequiredProperty("url"));
        dataSource.setPassword(myEnvironment.getRequiredProperty("password"));
        return dataSource;
    }

    @Bean
    public UserTemplateDAOImpl userDAO() {
        return new UserTemplateDAOImpl(dataSource());
    }

    @Bean
    public RoleTemplateDAOImpl roleDAOImpl() {
        return new RoleTemplateDAOImpl(dataSource());
    }

    @Bean
    public GrantsTemplateDAOImpl grantsDAOImpl(){
        return new GrantsTemplateDAOImpl(dataSource());
    }

}
