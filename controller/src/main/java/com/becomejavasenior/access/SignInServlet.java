package com.becomejavasenior.access;


import com.becomejavasenior.user.dto.User;
import com.becomejavasenior.user.services.IncorrectDataException;
import com.becomejavasenior.user.services.UserService;
import com.becomejavasenior.user.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "SignIn", urlPatterns = "/signin")
public class SignInServlet extends HttpServlet{

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        List<String> errors = new ArrayList<>();
        if (login != null) {
            if (login.trim().isEmpty()) {
                errors.add("Please enter email");
            } else if (!login.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                errors.add("Invalid email, please try again.");
            }
        }
        if (password != null && password.trim().isEmpty()) {
            errors.add("Please enter password");
        }
        if (errors.size() != 0){
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("WEB-INF/views/sign_in.jsp").forward(req, resp);
            return;
        }
        try {
            User user = userService.login(login, password);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/");
        } catch (IncorrectDataException ex){
            req.setAttribute("errors", Arrays.asList(ex.getMessage()));
            req.getRequestDispatcher("WEB-INF/views/sign_in.jsp").forward(req,resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/sign_in.jsp").forward(req, resp);
    }


}
