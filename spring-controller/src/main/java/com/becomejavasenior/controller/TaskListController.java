package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kramar on 24.02.16.
 */
@Controller
public class TaskListController {

    private static final Logger LOGGER = LogManager.getLogger(TaskListController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

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

    @RequestMapping(value="/tasklist", method = RequestMethod.GET)
    public String displayTaskListPage(Model model) {

        try {

        List<Task> allTasks;
//        if(request.getParameter("filtername")!=null){
//            allTasks = taskService.getTasksByParameters(request.getParameterMap());
//        }else {
            allTasks = taskService.getAllTask();
//        }
        Collections.sort(allTasks, (o1, o2) -> {
            long result = o1.getDueTime().getTime()-o2.getDueTime().getTime();
            if(result<0){return -1;}
            else if(result>0){return 1;}
            else return 0;
        });

        model.addAttribute("tasklist", allTasks);
        Calendar tomorowDate = GregorianCalendar.getInstance();
        tomorowDate.set(Calendar.HOUR_OF_DAY, 0);
        tomorowDate.set(Calendar.MINUTE, 0);
        tomorowDate.set(Calendar.SECOND, 0);
        tomorowDate.set(Calendar.MILLISECOND, 0);
        tomorowDate.add(Calendar.DAY_OF_MONTH, 1);
        model.addAttribute("tomorowdate", tomorowDate);
        Calendar endOfDay = (Calendar)tomorowDate.clone();
        endOfDay.add(Calendar.MINUTE, -1);
        model.addAttribute("endofday", endOfDay);
        model.addAttribute("timelist", getTimeList());
        model.addAttribute("tasktypes", taskService.getAllTaskTypes());
        model.addAttribute("users", userService.getAllUsers());

        } catch (DataBaseException | ServiceException e) {
            LOGGER.error(e);
        }

        return "tasklist";

    }



    /*
    @RequestMapping(value = "/tasklist", method = RequestMethod.POST)
    public String dashboard(Model model){
        model.addAllAttributes(dashboardService.getDashboardInformation());
        return "dashboard";
    }
    */

}
