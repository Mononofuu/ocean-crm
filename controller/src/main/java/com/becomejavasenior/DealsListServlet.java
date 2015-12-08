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
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by kramar on 19.11.15.
 */
@WebServlet("/dealslist")
public class DealsListServlet extends HttpServlet{
    static final Logger logger = LogManager.getRootLogger();
    private DaoFactory dao;
    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
            GenericDao dealStatusDao = dao.getDao(DealStatus.class);
            GenericDao dealDao = dao.getDao(Deal.class);
            List<DealStatus> dealStatusList = dealStatusDao.readAll();
            List<Deal> dealsList;
            String dealStatusId = request.getParameter("dealstatus");
//            if(dealStatusId == null){
                dealsList = dealDao.readAll();
//            }else {
//                dealsList = dealDao.readAll();
//            }
            request.setAttribute("deals", dealsList);
            request.setAttribute("deals_statuses", dealStatusList);
            request.getRequestDispatcher("jsp/dealslist.jsp").forward(request, response);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for dealslist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for dealslist.jsp",e);
        }
    }

}



