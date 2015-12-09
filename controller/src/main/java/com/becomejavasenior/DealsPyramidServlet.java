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
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@WebServlet("/dealspyramid")
public class DealsPyramidServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealController.class);
    private final static String nextJSP = "/jsp/dealspyramid.jsp";
    private DaoFactory dao;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        try {
            GenericDao<Deal> dealDao = dao.getDao(Deal.class);
            List<Deal> deals = dealDao.readAll();
            List<Deal> primaryDeals = deals.stream().filter(deal -> deal.getStatus().getName().equals("PRIMARY CONTACT")).collect(Collectors.toList());
            List<Deal> conversationDeals = deals.stream().filter(deal -> deal.getStatus().getName().equals("CONVERSATION")).collect(Collectors.toList());
            List<Deal> decisionDeals = deals.stream().filter(deal -> deal.getStatus().getName().equals("MAKE THE DECISION")).collect(Collectors.toList());
            List<Deal> approvalDeals = deals.stream().filter(deal -> deal.getStatus().getName().equals("APPROVAL OF THE CONTRACT")).collect(Collectors.toList());
            int primaryDealsBudget = primaryDeals.stream().mapToInt(i -> i.getBudget()).sum();
            int conversationDealsBudget = conversationDeals.stream().mapToInt(i -> i.getBudget()).sum();
            int decisionDealsBudget = decisionDeals.stream().mapToInt(i -> i.getBudget()).sum();
            int approvalDealsBudget = approvalDeals.stream().mapToInt(i -> i.getBudget()).sum();

            req.setAttribute("primaryDeals", primaryDeals);
            req.setAttribute("conversationDeals", conversationDeals);
            req.setAttribute("decisionDeals", decisionDeals);
            req.setAttribute("approvalDeals", approvalDeals);
            req.setAttribute("primaryDealsBudget", primaryDealsBudget);
            req.setAttribute("conversationDealsBudget", conversationDealsBudget);
            req.setAttribute("decisionDealsBudget", decisionDealsBudget);
            req.setAttribute("approvalDealsBudget", approvalDealsBudget);

        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
        logger.info(String.format("REDIRECTING TO %s", nextJSP));
    }
}
