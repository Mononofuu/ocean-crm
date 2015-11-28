package com.becomejavasenior;

import com.becomejavasenior.impl.DealDAOImpl;
import com.becomejavasenior.DataBaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kramar on 19.11.15.
 */
@WebServlet("/dealslist")
public class DealsListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

            DaoFactory daoFactory = null;
            daoFactory = new PostgreSqlDaoFactory();
            Connection connection = daoFactory.getConnection();
            GenericDao<Deal> dealDao = daoFactory.getDao(connection, Deal.class);
            List<Deal> dealsList = dealDao.readAll();

            req.setAttribute("deals", dealsList);

            req.getRequestDispatcher("dealslist.jsp").forward(req, resp);

        }catch (DataBaseException e){
            e.printStackTrace();
        }


    }
}



