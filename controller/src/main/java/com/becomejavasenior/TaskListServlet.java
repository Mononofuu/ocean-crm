package com.becomejavasenior;

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
                    long result = o1.getDueTime().getTime()-o2.getDueTime().getTime();
                    if(result<0){return -1;}
                    else if(result>0){return 1;}
                    else return 0;
                }
            });
            Enumeration<String> enumer = request.getParameterNames();
            if(enumer.hasMoreElements()){
                filterTasks(request, allTasks);
            }
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
            GenericDao<TaskType> taskTypesDao = dao.getDao(TaskType.class);
            request.setAttribute("tasktypes", taskTypesDao.readAll());
            GenericDao<TaskType> usersDao = dao.getDao(User.class);
            request.setAttribute("users", usersDao.readAll());
            getServletContext().getRequestDispatcher("/jsp/tasklist.jsp").forward(request,response);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        }
    }

    private List<String> getTimeList(){
        List<String> result = new ArrayList<>();
        Calendar c = GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int nextDay = c.get(Calendar.DAY_OF_MONTH)+1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        while(c.get(Calendar.DAY_OF_MONTH)!=nextDay){
            result.add(dateFormat.format(c.getTime()));
            c.add(Calendar.MINUTE, 30);
        }
        return result;
    }

    private void filterTasks(HttpServletRequest req, List<Task> tasks){
        String filtername = req.getParameter("filtername");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date dueDate = null;
        try {
            String date = req.getParameter("duedate");
            String time = req.getParameter("duetime");
            if("".equals(time)){
                time = "23:59";
            }
            dueDate = dateFormat.parse(date+" "+time);
        } catch (ParseException e) {
            logger.error("Неверный формат даты", e);
        }

        String taskType = req.getParameter("tasktype");
        logger.info(taskType);
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()){
            Task task = taskIterator.next();
            if(dueDate!=null&&task.getDueTime().getTime()>dueDate.getTime()){
                taskIterator.remove();
                continue;
            }
            if(taskType!=null&&!"".equals(taskType)&&TaskType.valueOf(taskType)!=task.getType()){
                taskIterator.remove();
                continue;
            }
        }
    }
}
