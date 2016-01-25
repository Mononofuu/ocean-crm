package com.becomejavasenior.config;

import com.becomejavasenior.Grants;
import com.becomejavasenior.Role;
import com.becomejavasenior.User;
import com.becomejavasenior.impl.GrantsTemplateDAOImpl;
import com.becomejavasenior.impl.RoleTemplateDAOImpl;
import com.becomejavasenior.impl.UserTemplateDAOImpl;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import com.becomejavasenior.interfacedao.UserDAO;
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
    public GenericTemplateDAO<Role> roleDao() {
        return new RoleTemplateDAOImpl();
    }

    @Bean
    public GenericTemplateDAO<Grants> grantsDao() {
        return new GrantsTemplateDAOImpl();
    }

}
