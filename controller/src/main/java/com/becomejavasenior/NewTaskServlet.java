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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="newtask", urlPatterns = "/newtask")
public class NewTaskServlet extends HttpServlet{
    private static final Logger LOGGER = LogManager.getLogger(NewTaskServlet.class);
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        req.setCharacterEncoding("UTF-8");
        try {
            taskService.saveTask(getTaskFromRequest(req));
            getServletContext().getRequestDispatcher("/tasklist").forward(req,resp);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
    }

    private Task getTaskFromRequest(HttpServletRequest request)throws DataBaseException, ServiceException{
        Task task = new Task();
        task.setUser(getUserFromRequest(request.getParameter("taskresponsible")));
        int subjectId = Integer.parseInt(request.getParameter("subject"));
        task.setSubject(taskService.getSubject(subjectId));
        String taskType = request.getParameter("tasktype");
        if (taskType != null && !"".equals(taskType)) {
            task.setType(TaskType.valueOf(taskType));
        }
        task.setComment(request.getParameter("tasktext"));
        task.setDateCreated(new Date());
        if(!"".equals(request.getParameter("duedate"))){
            String duedate = request.getParameter("duedate");
            String duetime = request.getParameter("duetime");
            if("".equals(duetime)){
                duetime = "23:59";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
            try {
                task.setDueTime(dateFormat.parse(duedate+duetime));
            } catch (ParseException e) {
                LOGGER.error(e);
            }
        }else{
            String period = request.getParameter("period");
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
                    c.set(Calendar.DAY_OF_WEEK, 0);
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
            task.setDueTime(c.getTime());
        }
        return task;
    }

    private User getUserFromRequest(String id)throws ServiceException{
        User result=null;
        if(id!=null){
            int key = Integer.parseInt(id);
            result = userService.findUserById(key);
        }
        return result;
    }
}
