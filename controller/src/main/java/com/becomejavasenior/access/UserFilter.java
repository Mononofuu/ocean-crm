package com.becomejavasenior.access;


import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final static Map<String, List<String>> URL2ROLES;

    static {
        URL2ROLES = new HashMap<>();
        List<String> dealslistUsers = new ArrayList<>();
        dealslistUsers.add("admin");
        dealslistUsers.add("user");
        List<String> dashboardUsers = new ArrayList<>();
        dashboardUsers.add("admin");
        dashboardUsers.add("user");
        List<String> contactsUsers = new ArrayList<>();
        contactsUsers.add("admin");
        contactsUsers.add("user");
        List<String> tasklistUsers = new ArrayList<>();
        tasklistUsers.add("admin");
        tasklistUsers.add("user");
        List<String> settingsUsers = new ArrayList<>();
        settingsUsers.add("admin");
        URL2ROLES.put("/dealslist", dealslistUsers);
        URL2ROLES.put("/dashboard", dashboardUsers);
        URL2ROLES.put("/contacts", contactsUsers);
        URL2ROLES.put("/tasklist", tasklistUsers);
        URL2ROLES.put("/settings", settingsUsers);

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
            for (Map.Entry<String, List<String>> url2role : URL2ROLES.entrySet()) {
                if (!path.contains(url2role.getKey())) {
                    continue;
                }
                for (String role : url2role.getValue()) {
                    if (role.equals(grant.getRole().getName())) {
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