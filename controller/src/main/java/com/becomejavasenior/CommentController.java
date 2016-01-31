package com.becomejavasenior;

import com.becomejavasenior.interfacedao.CommentDAO;
import com.becomejavasenior.interfacedao.SubjectDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Peter on 26.12.2015.
 */
@WebServlet("/commentedit")
public class CommentController extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealController.class);
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private SubjectDAO subjectDAO;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "update":
                Comment comment;
                try {
                    int id = getId(request);
                    if (id == 0) {
                        comment = new Comment();
                        Subject subject = subjectDAO.read(Integer.parseInt(request.getParameter("subjectid")));
                        comment.setSubject(subject);
                        comment.setDateCreated(new Date());
                        comment.setUser((User) request.getSession().getAttribute("user"));
                        comment.setText(request.getParameter("commenttext"));
                        commentDAO.create(comment);
                        logger.info("Comment created:");
                        logger.info(comment.getId());
                        logger.info(comment.getSubject());
                        logger.info(comment.getText());
                        logger.info(comment.getDateCreated());
                        logger.info(comment.getUser());
                    } else {
                        comment = commentDAO.read(getId(request));
                        comment.setText(request.getParameter("commenttext"));
                        commentDAO.update(comment);
                        logger.info("Comment updated:");
                        logger.info(comment.getId());
                        logger.info(comment.getText());
                    }
                    request.getRequestDispatcher(request.getParameter("backurl")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while updating comment");
                    logger.catching(e);
                }
                break;
            case "delete":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    commentDAO.delete(id);
                    logger.info("Comment deleted:");
                    logger.info("id=" + id);
                    request.getRequestDispatcher(request.getParameter("backurl") + "&id=" + request.getParameter("subjectid")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while deleting comment");
                    logger.catching(e);
                }
                break;
            case "edit":
                try {
                    int id = getId(request);
                    if (id == 0) {
                        comment = new Comment();
                        request.setAttribute("subjectid", request.getParameter("subjectid"));
                    } else {
                        comment = commentDAO.read(getId(request));
                    }
                    request.setAttribute("comment", comment);
                    request.setAttribute("backurl", request.getParameter("backurl") + "&id=" + request.getParameter("subjectid"));
                    request.getRequestDispatcher("jsp/commentedit.jsp").forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while editing comment");
                    logger.catching(e);
                }
                break;
            default:
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }


}
