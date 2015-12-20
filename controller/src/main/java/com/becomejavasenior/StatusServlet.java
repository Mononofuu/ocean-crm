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
import java.util.Objects;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@WebServlet("/deal_status")
public class StatusServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealsPyramidServlet.class);
    private final static String nextJSP = "/jsp/dealsstatus.jsp";
    private final static String editStatusJSP = "/jsp/statusedit.jsp";
    private DaoFactory dao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        DealStatus status = new DealStatus();
        status.setName(req.getParameter("name"));
        status.setColor(req.getParameter("color"));

        try {
            dao = new PostgreSqlDaoFactory();
            GenericDao<DealStatus> statusDao = dao.getDao(DealStatus.class);
            if (action.equals("create")) {
                statusDao.create(status);
                logger.info("NEW STATUS CREATED:");
            } else if (action.equals("edit")) {
                status.setId(getId(req));
                statusDao.update(status);
                logger.info("STATUS UPDATED:");
            }
            statusDao.create(status);
            logger.info("NEW STATUS CREATED:");
            logger.info(status.getId());
            logger.info(status.getName());

        } catch (DataBaseException e) {
            logger.error("Error while creating new status");
            logger.catching(e);
        }
        resp.sendRedirect("/deal_status");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        try {
            GenericDao<DealStatus> statusDao = dao.getDao(DealStatus.class);

            if (action == null) {

                List<DealStatus> statusList = statusDao.readAll();

                req.setAttribute("statusList", statusList);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                dispatcher.forward(req, resp);
                logger.info(String.format("REDIRECTING TO %s", nextJSP));

            } else if (action.equals("delete")) {
                int id = getId(req);
                statusDao.delete(id);
                logger.info("Delete status {}", id);
                resp.sendRedirect("deal_status");
            } else if (action.equals("edit")) {
                int id = getId(req);
                DealStatus editStatus = statusDao.read(id);
                logger.info("Edit status {}", id);
                req.setAttribute("status", editStatus);
                req.getRequestDispatcher(editStatusJSP).forward(req, resp);
            }

        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
