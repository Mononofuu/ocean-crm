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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

            List<Deal> dealsList = new ArrayList<Deal>();

            dao = new PostgreSqlDaoFactory();

            GenericDao dealStatusDao = dao.getDao(DealStatus.class);
            List<DealStatus> dealStatusList = dealStatusDao.readAll();

            GenericDao userDao = dao.getDao(User.class);
            List<User> userList = userDao.readAll();


//            GenericDao dealDao = dao.getDao(Deal.class);
            DealDAOImpl dealDao = new DealDAOImpl();

            String dealStatusId = request.getParameter("dealstatus");
            String dealUserId = request.getParameter("user");
            String tags = request.getParameter("tags");

            List<List<Deal>> list = new ArrayList<>();

            if(dealStatusId != null && dealStatusId.equals("") == false){
                dealsList = dealDao.readStatusFilter(Integer.valueOf(dealStatusId));
                list.add(dealsList);
                logger.info("DealsListServlet. Used filter dealStatusId " + dealStatusId);
            }

            if(dealUserId != null && dealUserId.equals("") ==false){
                dealsList = dealDao.readUserFilter(Integer.valueOf(dealUserId));
                list.add(dealsList);
                logger.info("DealsListServlet. Used filter dealUserId " + dealUserId);
            }

            if(tags != null){
                String tag = tags.trim().replaceAll("\\s+","','");
                if(!tag.equals("")) {
                    dealsList = dealDao.readTagFilter("'" + tag + "')))");
                    list.add(dealsList);
                    logger.info("DealsListServlet. Used filter dealTag " + tag);
                }
            }

            if(list.size() == 0){
                dealsList = dealDao.readAll();
            }else{
                List<Deal> dealsListTemp;
                Iterator<List<Deal>> listIterator = list.iterator();
                dealsList = listIterator.next();
                while (dealsList.size() != 0 && listIterator.hasNext()) {
                    //                   dealsListTemp = listIterator.next();
                    dealsList = dealsList.stream()
                            .filter(deal ->
//                                (dealsListTemp.stream().map(Deal::getId).collect(Collectors.toList())).contains(deal.getId()))
                                    (listIterator.next().stream().map(Deal::getId).collect(Collectors.toList())).contains(deal.getId()))
                            .collect(Collectors.toList());
                }
            }

            request.setAttribute("deals", dealsList);
            request.setAttribute("deals_statuses", dealStatusList);
            request.setAttribute("users", userList);
            request.getRequestDispatcher("jsp/dealslist.jsp").forward(request, response);
//            connection.close();
//        } catch (SQLException e) {
//            logger.error("Error when prepearing data for dealslist.jsp",e);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for dealslist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for dealslist.jsp",e);
        }
    }

}
