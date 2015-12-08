package com.becomejavasenior.access;


import com.becomejavasenior.user.dto.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@WebFilter (filterName = "UserFilter", initParams = @WebInitParam(name = "roles", value = "user,admin"))
public class UserFilter implements Filter{
    private ArrayList<String> roles;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String rawRoles = filterConfig.getInitParameter("roles");
        StringTokenizer tokenizer = new StringTokenizer(rawRoles, ",");
        roles = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            roles.add(tokenizer.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        User loggedUser = ((User)(((HttpServletRequest) servletRequest).getSession().getAttribute("user")));
        if (roles.contains(loggedUser.getRole())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else{
            response.sendRedirect(response.encodeRedirectURL("/"));
        }
    }

    @Override
    public void destroy() {

    }
}
