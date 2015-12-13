package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="tasklist", urlPatterns = "/tasklist")
public class TaskListServlet extends HttpServlet{
    private Logger logger = LogManager.getLogger(TaskListServlet.class);
    private DaoFactory dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            dao = new PostgreSqlDaoFactory();
            GenericDao taskDao = dao.getDao(Task.class);
            List<Task> allTasks = taskDao.readAll();
            Collections.sort(allTasks, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    return (int)o1.getDueTime().getTime()-(int)o2.getDueTime().getTime();
                }
            });
            request.setAttribute("tasklist", allTasks);
            Calendar c = GregorianCalendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            c.add(Calendar.DAY_OF_MONTH, 1);
            request.setAttribute("tomorowdate", c);
            getServletContext().getRequestDispatcher("/jsp/tasklist.jsp").forward(request,response);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        }
    }

}
