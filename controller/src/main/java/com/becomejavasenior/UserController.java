package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by kramar on 25.11.15.
 */
@WebServlet("/userController")
public class UserController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

                DaoFactory daoFactory = null;
                daoFactory = new PostgreSqlDaoFactory();
                Connection connection = daoFactory.getConnection();
                GenericDao<Company> companyDao = daoFactory.getDao(connection, Company.class);
                List<Company> companyList = companyDao.readAll();

                req.setAttribute("companies", companyList);

                req.getRequestDispatcher("user.jsp").forward(req, resp);

         }catch (DataBaseException e){
                e.printStackTrace();
         }





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("company"));

        try {
            DaoFactory daoFactory = new PostgreSqlDaoFactory();
            Connection connection = daoFactory.getConnection();
            GenericDao<User> userDao = daoFactory.getDao(connection, User.class);
            User user = new User();
            user.setName(req.getParameter("name"));
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setPassword(req.getParameter("email"));
            user.setPhoneHome(req.getParameter("phone_mob"));
            user.setPhoneWork(req.getParameter("phone_work"));
            switch (req.getParameter("language")){
                case "EN":
                    user.setLanguage(Language.EN);
                    break;
                case "RU":
                    user.setLanguage(Language.RU);
                    break;
            }
            userDao.create(user);

            GenericDao<Company> companyDao = daoFactory.getDao(connection, Company.class);
            List<Company> companyList = companyDao.readAll();
            req.setAttribute("companies", companyList);

        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("user.jsp").forward(req, resp);

    }
}
