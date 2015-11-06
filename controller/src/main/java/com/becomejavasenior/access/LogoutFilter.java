package com.becomejavasenior.access;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if ((session.getAttribute("user")) != null) {
            session.setAttribute("user", null);
            ((HttpServletResponse)servletResponse).sendRedirect("/");
        } else {
            ((HttpServletResponse)servletResponse).sendRedirect("/");
        }
    }

    @Override
    public void destroy() {

    }
}
