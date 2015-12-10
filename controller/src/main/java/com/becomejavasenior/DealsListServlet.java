package com.becomejavasenior;

import com.becomejavasenior.impl.CommentDAOImpl;
import com.becomejavasenior.impl.DealDAOImpl;

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

            List<Deal> dealsList;
            List<Deal> dealsListStatus;
            List<Deal> dealsListUser;
            List<Deal> dealsListTag;

            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();

            GenericDao dealStatusDao = dao.getDao(DealStatus.class);
            List<DealStatus> dealStatusList = dealStatusDao.readAll();

            GenericDao userDao = dao.getDao(User.class);
            List<User> userList = userDao.readAll();


//            GenericDao dealDao = dao.getDao(Deal.class);
            DealDAOImpl dealDao = new DealDAOImpl(connection);
            dealsList = dealDao.readAll();

            String dealStatusId = request.getParameter("dealstatus");
            String dealUserId = request.getParameter("user");


            if(dealStatusId == null || dealStatusId.equals("")){
                dealsListStatus = dealDao.readAll();
            }else {
                dealsListStatus = dealDao.readStatusFilter(Integer.valueOf(dealStatusId));
            }



            if(dealUserId == null || dealUserId.equals("")){
                dealsListUser = dealDao.readAll();
            }else {
                dealsListUser = dealDao.readUserFilter(Integer.valueOf(dealUserId));
            }


            String tags = request.getParameter("tags");
            if(tags == null){
                dealsListTag = dealDao.readAll();
            }else{
                String tag = tags.trim().replaceAll("\\s+","','");
//                String[] tag = tags.trim().split(" ");
//                if(tag.length == 0 ) {
                if(tag.equals("")) {
                    dealsListTag = dealDao.readAll();
                }else{
                    dealsListTag = dealDao.readTagFilter("'" + tag + "')))");
//                    dealsList = dealDao.readTagFilter(tag);
                }
            }

//            dealsList = dealsListStatus.and(dealsListTag);

            request.setAttribute("deals", dealsList);
            request.setAttribute("deals_statuses", dealStatusList);
            request.setAttribute("users", userList);
            request.getRequestDispatcher("jsp/dealslist.jsp").forward(request, response);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for dealslist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for dealslist.jsp",e);
        }
    }

}
