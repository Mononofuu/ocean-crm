package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private Logger logger = LogManager.getLogger(NewContactPrepareServlet.class);
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
            GenericDao companyDao = dao.getDao(Company.class);
            List<Company> companyList = companyDao.readAll();
            request.setAttribute("companylist", companyList);
            GenericDao userDao = dao.getDao(User.class);
            List<User> usersList = userDao.readAll();
            request.setAttribute("userslist", usersList);
            getServletContext().getRequestDispatcher("/jsp/newcontact.jsp").forward(request,response);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for newcontact.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for newcontact.jsp",e);
        }
    }
}
