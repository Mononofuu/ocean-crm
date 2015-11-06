package com.becomejavasenior.access;


import com.becomejavasenior.user.dto.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RoleFilter implements Filter{

    private ArrayList<String> roles;

    public void init(FilterConfig filterConfig) throws ServletException {
        String rawRoles = filterConfig.getInitParameter("roles");
        StringTokenizer tokenizer = new StringTokenizer(rawRoles, ",");
        roles = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            roles.add(tokenizer.nextToken());
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User loggedUser = (User)session.getAttribute("user");
        if(loggedUser != null){
            if (roles.contains(loggedUser.getRole())) {
                filterChain.doFilter(request, servletResponse);
                return;
            } else{
                ((HttpServletResponse)servletResponse).sendRedirect("/");
                return;
            }
        }
        ((HttpServletResponse)servletResponse).sendRedirect("/login");
    }

    public void destroy() {
    }
}
