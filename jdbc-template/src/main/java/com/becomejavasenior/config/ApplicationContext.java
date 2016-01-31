package com.becomejavasenior.config;

import com.becomejavasenior.*;
import com.becomejavasenior.impl.*;
import com.becomejavasenior.interfacedao.DashboardDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Lybachevskiy.Vladislav
 */
@Configuration
@ImportResource("classpath:spring-datasource.xml")
public class ApplicationContext {

    @Bean
    public GenericTemplateDAO<User> userDao() {
        return new UserTemplateDAOImpl();
    }

    @Bean
    public GenericTemplateDAO<Role> roleDao() {
        return new RoleTemplateDAOImpl();
    }

    @Bean
    public GenericTemplateDAO<Grants> grantsDao() {
        return new GrantsTemplateDAOImpl();
    }

}
