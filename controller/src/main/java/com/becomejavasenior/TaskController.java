package com.becomejavasenior;

import com.becomejavasenior.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                        User user = new UserServiceImpl().findUserById(Integer.parseInt(request.getParameter("user")));
                        task.setUser(user);
                        task.setDateCreated(new Date());
                        String duedate = request.getParameter("duedate");
                        if(!duedate.equals("")){
                            String duetime = request.getParameter("duetime");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
                            task.setDueTime(dateFormat.parse(duedate+duetime));
                        }else {
                            String period = request.getParameter("period");
                            task.setDueTime(getCalendarDate(period));
                        }
//                        task.setUser((User) request.getSession().getAttribute("user"));
                        task.setComment(request.getParameter("taskcomment"));
                        task.setType(TaskType.valueOf(request.getParameter("tasktype")));
                        taskDao.create(task);
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
                        String duedate = request.getParameter("duedate");
                        if(!duedate.equals("")){
                            String duetime = request.getParameter("duetime");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
                            task.setDueTime(dateFormat.parse(duedate+duetime));
                        }else {
                            String period = request.getParameter("period");
                            task.setDueTime(getCalendarDate(period));
                        }
                        User user = new UserServiceImpl().findUserById(Integer.parseInt(request.getParameter("user")));
                        task.setUser(user);
                        String submitname = request.getParameter("btn_task_update");
                        switch (submitname){
                            case "close":
                                task.setIsClosed((byte)1);
                                break;
                            case "delete":
                                task.setIsDeleted((byte)1);
                                break;
                            default:
                                break;
                        }
                        taskDao.update(task);
                        logger.info("task updated:");
                        logger.info(task.getId());
                        logger.info(task.getDueTime());
                        logger.info(task.getUser());
                        logger.info(task.getComment());
                        logger.info(task.getType());
                    }
                    request.getRequestDispatcher(request.getParameter("backurl")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while updating task");
                    logger.catching(e);
                } catch (ParseException e) {
                    logger.error("Error parse date");
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
                    GenericDao userDao = dao.getDao(User.class);
                    List<User> userList = userDao.readAll();
                    request.setAttribute("users", userList);
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

    private Date getCalendarDate(String period){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        switch (period){
            case "today":
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MINUTE, -1);
                break;
            case "allday":
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MINUTE, -1);
                break;
            case "tomorow":
                c.add(Calendar.DAY_OF_MONTH, 2);
                c.add(Calendar.MINUTE, -1);
                break;
            case "nextweek":
                c.set(Calendar.DAY_OF_WEEK, 2);
                c.add(Calendar.DAY_OF_MONTH,14);
                c.add(Calendar.MINUTE, -1);
                break;
            case "nextmonth":
                c.set(Calendar.DAY_OF_MONTH,0);
                c.add(Calendar.MONTH, 2);
                c.add(Calendar.MINUTE, -1);
                break;
            case "nextyear":
                c.set(Calendar.DAY_OF_MONTH,0);
                c.set(Calendar.MONTH,0);
                c.add(Calendar.YEAR, 2);
                c.add(Calendar.MINUTE, -1);
                break;
        }
        return c.getTime();
    }
}
