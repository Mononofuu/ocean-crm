package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@WebServlet("/dealspyramid")
public class DealsPyramidServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealsPyramidServlet.class);
    private final static String nextJSP = "/jsp/dealspyramid.jsp";
    private DaoFactory dao;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = String.valueOf(req.getParameter("selectedfilter"));
        logger.info(String.format("Selected filter: %s", filter));

        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        try {
            GenericDao<Deal> dealDao = dao.getDao(Deal.class);
            GenericDao<DealStatus> statusDao = dao.getDao(DealStatus.class);
            List<DealStatus> statuses = statusDao.readAll();
            Collections.sort(statuses);

            List<Deal> deals = applyFilter(dealDao.readAll(), filter);

            SortedMap<DealStatus, List<Deal>> dealsToStatus = new TreeMap<>();
            for (DealStatus status : statuses) {
                dealsToStatus.put(status, new ArrayList<>());
                deals.stream().filter(deal -> deal.getStatus().equals(status)).forEach(deal1 -> dealsToStatus.get(status).add(deal1));
            }

            req.setAttribute("deals_map", dealsToStatus);

        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
        logger.info(String.format("REDIRECTING TO %s", nextJSP));
    }

    private List<Deal> applyFilter(List<Deal> deals, String filter) {
        switch (filter) {
            case "open":
                return deals.stream().filter(deal -> deal.getDateWhenDealClose() == null)
                        .collect(Collectors.toList());
            case "my":
                return deals; //TODO
            case "success":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & deal.getStatus().getName().equals("SUCCESS"))
                        .collect(Collectors.toList());
            case "fail":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & deal.getStatus().getName().equals("FAIL"))
                        .collect(Collectors.toList());
            case "notask":
                return deals.stream()
                        .filter(deal -> deal.getTasks().isEmpty())
                        .collect(Collectors.toList());
            case "expired":
                return deals.stream()
                        .filter(deal -> (deal.getTasks()
                                .stream().filter(task -> task.getDueTime().before(new Date()))
                                .collect(Collectors.toList())) != null)
                        .collect(Collectors.toList());
            case "deleted":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & deal.getStatus().getName().equals("DELETED"))
                        .collect(Collectors.toList());
            default:
                return deals;
        }
    }
}
