package com.becomejavasenior.access;


import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "UserFilter")
public class UserFilter implements Filter {

    private final static Logger LOGGER = LogManager.getLogger(UserFilter.class);
    private final static Map<String, List<Role>> URL2ROLES;

    static {
        URL2ROLES = new HashMap<>();
        Role admin = new Role();
        admin.setName("admin");
        Role user = new Role();
        user.setName("user");
        List<Role> dealslistRoles = new ArrayList<>();
        dealslistRoles.add(admin);
        dealslistRoles.add(user);
        List<Role> dealeditRoles = new ArrayList<>();
        dealeditRoles.add(admin);
        dealeditRoles.add(user);
        List<Role> dashboardRoles = new ArrayList<>();
        dashboardRoles.add(admin);
        dashboardRoles.add(user);
        List<Role> contactsRoles = new ArrayList<>();
        contactsRoles.add(admin);
        contactsRoles.add(user);
        List<Role> companyRoles = new ArrayList<>();
        companyRoles.add(admin);
        companyRoles.add(user);
        List<Role> tasklistRoles = new ArrayList<>();
        tasklistRoles.add(admin);
        tasklistRoles.add(user);
        List<Role> commentRoles = new ArrayList<>();
        commentRoles.add(admin);
        commentRoles.add(user);
        List<Role> settingsRoles = new ArrayList<>();
        settingsRoles.add(admin);
        URL2ROLES.put("/dealslist", dealslistRoles);
        URL2ROLES.put("/dealedit", dealeditRoles);
        URL2ROLES.put("/companyedit", companyRoles);
        URL2ROLES.put("/contactedit", contactsRoles);
        URL2ROLES.put("/commentedit", commentRoles);
        URL2ROLES.put("/taskedit", tasklistRoles);
        URL2ROLES.put("/dashboard", dashboardRoles);
        URL2ROLES.put("/contactlist", contactsRoles);
        URL2ROLES.put("/tasklist", tasklistRoles);
        URL2ROLES.put("/settings", settingsRoles);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        Object userObject = ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (userObject != null && userObject instanceof User) {
            User loggedUserName = (User) userObject;
            try {
                GenericDao<Grants> grantsDao = new PostgreSqlDaoFactory().getDao(Grants.class);
                List<Grants> grants = grantsDao.readAll();
                if (isAllowedToUser(path, loggedUserName, grants)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            } catch (DataBaseException e) {
                LOGGER.error(e.getMessage());
            }
        }
        servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
    }

    private boolean isAllowedToUser(String path, User loggedUserName, List<Grants> grants) throws IOException, ServletException {
        for (Grants grant : grants) {
            if (grant.getUser().getId() != loggedUserName.getId()) {
                continue;
            }
            for (Map.Entry<String, List<Role>> url2role : URL2ROLES.entrySet()) {
                if (!path.contains(url2role.getKey())) {
                    continue;
                }
                for (Role role : url2role.getValue()) {
                    if (role.equals(grant.getRole())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}