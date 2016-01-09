package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Peter on 26.12.2015.
 */
@WebServlet("/taskedit")
public class TaskController extends HttpServlet{
    private final static Logger logger = LogManager.getLogger(DealController.class);
    private DaoFactory dao;
    private Task task;

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
                try {
                    dao = new PostgreSqlDaoFactory();
                    GenericDao<Task> taskDao = dao.getDao(Task.class);
                    int id = getId(request);
                    if(id==0){
                        task = new Task();
                        GenericDao<Subject> subjectDao = dao.getDao(Subject.class);
                        Subject subject = subjectDao.read(Integer.parseInt(request.getParameter("subjectid")));
                        task.setSubject(subject);
                        task.setDateCreated(new Date());
                        task.setDueTime(new Date());
                        task.setUser((User) request.getSession().getAttribute("user"));
                        task.setComment(request.getParameter("taskcomment"));
                        task.setType(TaskType.valueOf(request.getParameter("tasktype")));
//                        taskDao.create(task);
                        logger.info("task created:");
                        logger.info(task.getId());
                        logger.info(task.getSubject());
                        logger.info(task.getDateCreated());
                        logger.info(task.getDueTime());
                        logger.info(task.getUser());
                        logger.info(task.getComment());
                        logger.info(task.getType());
                    }else{
                        task = (Task) taskDao.read(getId(request));
                        task.setComment(request.getParameter("taskcomment"));
                        task.setType(TaskType.valueOf(request.getParameter("tasktype")));
//                        taskDao.update(task);
                        logger.info("task updated:");
                        logger.info(task.getId());
                        logger.info(task.getComment());
                        logger.info(task.getType());
                    }
                    request.getRequestDispatcher(request.getParameter("backurl")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while updating task");
                    logger.catching(e);
                }
                break;
            case "delete":
                try {
                    dao = new PostgreSqlDaoFactory();
                    GenericDao<Task> taskDao = dao.getDao(Task.class);
                    int id = Integer.parseInt(request.getParameter("id"));
                    taskDao.delete(id);
                    logger.info("task deleted:");
                    logger.info("id="+id);
                    request.getRequestDispatcher(request.getParameter("backurl")+"&id="+request.getParameter("subjectid")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while deleting task");
                    logger.catching(e);
                }
                break;
            case "edit":
                try {
                    dao = new PostgreSqlDaoFactory();
                    int id = getId(request);
                    GenericDao taskTypeDao = dao.getDao(TaskType.class);
                    List<TaskType> taskTypeList = taskTypeDao.readAll();
                    request.setAttribute("tasktypes", taskTypeList);
                    if(id==0) {
                        task = new Task();
                        task.setType(TaskType.MEETING);
                        request.setAttribute("subjectid", request.getParameter("subjectid"));
                    }else{
                        GenericDao<Task> taskDao = dao.getDao(Task.class);
                        task = (Task) taskDao.read(getId(request));
                    }
                    request.setAttribute("task", task);
                    request.setAttribute("backurl",request.getParameter("backurl")+"&id="+request.getParameter("subjectid"));
                    request.getRequestDispatcher("jsp/taskedit.jsp").forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while editing task");
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
