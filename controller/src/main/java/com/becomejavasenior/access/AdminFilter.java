package com.becomejavasenior.access;

import com.becomejavasenior.UserWithRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter (filterName = "AdminFilter", initParams = @WebInitParam(name = "role", value = "admin"))
public class AdminFilter implements Filter{

    private String allowableRole;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowableRole = filterConfig.getInitParameter("role");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (((UserWithRole)request.getSession().getAttribute("user")).getRole().equals(allowableRole)){
            filterChain.doFilter(request, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendRedirect("/");
        }
    }

    @Override
    public void destroy() {

    }
}
