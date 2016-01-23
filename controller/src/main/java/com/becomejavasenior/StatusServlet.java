package com.becomejavasenior;

import com.becomejavasenior.impl.DealServiceImpl;
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
import java.util.Objects;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@WebServlet("/deal_status")
public class StatusServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DealsPyramidServlet.class);
    private static final String nextJSP = "/jsp/dealsstatus.jsp";
    private static final String editStatusJSP = "/jsp/statusedit.jsp";
    private static DealService dealService = new DealServiceImpl();


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = getId(req);
            dealService.deleteDealStatus(id);
            LOGGER.info("Delete status {}", id);
        } catch (DataBaseException e) {
            LOGGER.error("Error while deleting status");
            LOGGER.catching(e);
        }
        resp.sendRedirect("deal_status");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DealStatus status = new DealStatus();
        status.setName(req.getParameter("name"));
        status.setColor(req.getParameter("color"));
        try {
            dealService.saveDealStatus(status);
        } catch (DataBaseException e) {
            LOGGER.error("Error while creating new status");
            LOGGER.catching(e);
        }
        resp.sendRedirect("/deal_status");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            if ("create".equals(action)) {
                doPut(req, resp);
            } else if ("edit".equals(action)) {
                DealStatus status = new DealStatus();
                status.setName(req.getParameter("name"));
                status.setColor(req.getParameter("color"));
                status.setId(getId(req));
                dealService.saveDealStatus(status);
                LOGGER.info("STATUS UPDATED:");
                resp.sendRedirect("/deal_status");
            }
        } catch (DataBaseException e) {
            LOGGER.error("Error while updating status");
            LOGGER.catching(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if (action == null) {
                List<DealStatus> statusList = dealService.getAllDealStatuses();
                req.setAttribute("statusList", statusList);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                dispatcher.forward(req, resp);
                LOGGER.info(String.format("REDIRECTING TO %s", nextJSP));
            } else if ("delete".equals(action)) {
                doDelete(req, resp);
            } else if ("edit".equals(action)) {
                int id = getId(req);
                DealStatus editStatus = dealService.findDealStatus(id);
                LOGGER.info("Edit status {}", id);
                req.setAttribute("status", editStatus);
                req.getRequestDispatcher(editStatusJSP).forward(req, resp);
            }

        } catch (DataBaseException e) {
            LOGGER.error("Error while getting DAO");
            LOGGER.catching(e);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
