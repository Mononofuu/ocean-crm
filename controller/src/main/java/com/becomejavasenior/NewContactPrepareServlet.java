package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name="new_contact_prepare", urlPatterns = "/new_contact_prepare")
public class NewContactPrepareServlet extends HttpServlet {
    private DaoFactory dao;
    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
            GenericDao companyDao = dao.getDao(connection,Company.class);
            List<Company> companyList = companyDao.readAll();
            request.setAttribute("companylist", companyList);
            GenericDao userDao = dao.getDao(connection,User.class);
            List<User> usersList = userDao.readAll();
            request.setAttribute("userslist", usersList);
            getServletContext().getRequestDispatcher("/jsp/newcontact.jsp").forward(request,response);
        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
