package com.becomejavasenior.access;

import com.becomejavasenior.Grants;
import com.becomejavasenior.User;

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
        User user = (User)request.getSession().getAttribute("user");
        for(Grants userGrant : user.getGrantsSet()) {
            if (allowableRole.equals(userGrant.getRole().getName())) {
                filterChain.doFilter(request, servletResponse);
            }
        }
        ((HttpServletResponse)servletResponse).sendRedirect("/");
    }

    @Override
    public void destroy() {

    }
}
