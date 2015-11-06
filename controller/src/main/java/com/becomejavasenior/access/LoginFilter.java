package com.becomejavasenior.access;

import com.becomejavasenior.user.dto.User;
import com.becomejavasenior.user.services.UserService;
import com.becomejavasenior.user.services.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter{

    private UserService userService = new UserServiceImpl();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //TODO data verification & save requested path
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getMethod().equalsIgnoreCase("POST") && request.getSession().getAttribute("user") == null){
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            //TODO add exception?
            User user = userService.login(login, password);
            request.getSession().setAttribute("user", user);
            ((HttpServletResponse)servletResponse).sendRedirect("/");
            return;
        } else if (request.getMethod().equalsIgnoreCase("GET") && request.getSession().getAttribute("user") == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, servletResponse);
            return;
        }
        ((HttpServletResponse) servletResponse).sendRedirect("/");
    }

    @Override
    public void destroy() {

    }
}
