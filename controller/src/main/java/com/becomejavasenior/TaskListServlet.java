package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="tasklist", urlPatterns = "/tasklist")
public class TaskListServlet extends HttpServlet{
    private static final Logger LOGGER = LogManager.getLogger(TaskListServlet.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    private static List<String> getTimeList() {
        List<String> result = new ArrayList<>();
        Calendar c = GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, 1);
        int nextDay = c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        while (c.get(Calendar.DAY_OF_MONTH) != nextDay) {
            result.add(dateFormat.format(c.getTime()));
            c.add(Calendar.MINUTE, 30);
        }
        return result;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

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
            List<Task> allTasks;
            if(request.getParameter("filtername")!=null){
                allTasks = taskService.getTasksByParameters(request.getParameterMap());
            }else {
                allTasks = taskService.getAllTask();
            }
            Collections.sort(allTasks, (o1, o2) -> {
                long result = o1.getDueTime().getTime()-o2.getDueTime().getTime();
                if(result<0){return -1;}
                else if(result>0){return 1;}
                else return 0;
            });

            request.setAttribute("tasklist", allTasks);
            Calendar tomorowDate = GregorianCalendar.getInstance();
            tomorowDate.set(Calendar.HOUR_OF_DAY, 0);
            tomorowDate.set(Calendar.MINUTE, 0);
            tomorowDate.set(Calendar.SECOND, 0);
            tomorowDate.set(Calendar.MILLISECOND, 0);
            tomorowDate.add(Calendar.DAY_OF_MONTH, 1);
            request.setAttribute("tomorowdate", tomorowDate);
            Calendar endOfDay = (Calendar)tomorowDate.clone();
            endOfDay.add(Calendar.MINUTE, -1);
            request.setAttribute("endofday", endOfDay);
            request.setAttribute("timelist", getTimeList());
            request.setAttribute("tasktypes", taskService.getAllTaskTypes());
            request.setAttribute("users", userService.getAllUsers());
            getServletContext().getRequestDispatcher("/jsp/tasklist.jsp").forward(request,response);
        } catch (DataBaseException e) {
            LOGGER.error("Error when prepearing data for tasklist.jsp",e);
        } catch (ServletException e) {
            LOGGER.error("Error when prepearing data for tasklist.jsp",e);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
    }
}
