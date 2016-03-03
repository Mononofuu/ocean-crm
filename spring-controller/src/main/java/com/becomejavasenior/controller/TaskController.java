package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import com.becomejavasenior.validation.TaskFormHandler;
import com.becomejavasenior.validation.TaskFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kramar on 24.02.16.
 */
@Controller
public class TaskController {

    private static final Logger LOGGER = LogManager.getLogger(TaskListController.class);

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    DealService dealService;

    @Autowired
    ContactService contactService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private TaskFormValidator taskFormValidator;

    @Autowired
    private ConversionService conversionService;

    @InitBinder()
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(taskFormValidator);
    }

    @RequestMapping(value = "/newtask", method = RequestMethod.GET)
    public String displayNewTask(Model model) {
        setTaskFormAttributes(model);
        model.addAttribute("taskForm", new TaskFormHandler());
        return "taskform";
    }

    @RequestMapping(value = "/newtask", method = RequestMethod.POST)
    public String saveOrUpdateTask(@ModelAttribute("taskForm") @Validated TaskFormHandler taskForm,
                                   BindingResult result, Model model,
                                   @RequestParam Map<String, String> parameters) throws ServiceException {
        if (result.hasErrors()) {
            LOGGER.debug(result.toString());
            setTaskFormAttributes(model);
            return "taskform";
        }
        try {
            Task task = taskForm.getTask();
            task.setDateCreated(new Date());
            if (!"".equals(taskForm.getDueDate())) {
                String duedate = taskForm.getDueDate();
                String duetime = taskForm.getDueTime();
                if("".equals(duetime)){
                    duetime = "23:59";
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
                try {
                    task.setDueTime(dateFormat.parse(duedate + duetime));
                } catch (ParseException e) {
                    LOGGER.error(e);
                }
            } else {
                String period = taskForm.getPeriod();
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                switch (period) {
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
                    case "nextyear":
                        c.set(Calendar.DAY_OF_MONTH, 0);
                        c.set(Calendar.MONTH, 0);
                        c.add(Calendar.YEAR, 2);
                        c.add(Calendar.MINUTE, -1);
                        break;
                    case "nextweek":
                        c.set(Calendar.DAY_OF_WEEK, 0);
                        c.add(Calendar.DAY_OF_MONTH, 14);
                        c.add(Calendar.MINUTE, -1);
                        break;
                    case "nextmonth":
                        c.set(Calendar.DAY_OF_MONTH, 0);
                        c.add(Calendar.MONTH, 2);
                        c.add(Calendar.MINUTE, -1);
                        break;

                }
                task.setDueTime(c.getTime());
            }
            taskService.saveTask(task);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }

        return "redirect:tasklist";

    }

    private void setTaskFormAttributes(Model model) {

        try {

            List<User> usersList = userService.getAllUsers();
            for (User user : usersList) {
                user.setComments(null);
                user.setEvents(null);
                user.setFiles(null);
                user.setRoles(null);
                user.setTasks(null);
            }
            model.addAttribute("userslist", usersList);

            List<TaskType> taskTypeList = taskService.getAllTaskTypes();
            model.addAttribute("tasktypes", taskTypeList);

            List<Deal> dealList = dealService.findDeals();
            for (Deal deal : dealList) {
                deal.setComments(null);
                deal.setContacts(null);
                deal.setFiles(null);
                deal.setTasks(null);
                deal.setUser(null);
                deal.setTags(null);
            }

            List<Contact> contactList = contactService.findContacts();
            for (Contact contact : contactList) {
                contact.setComments(null);
                contact.setDeals(null);
                contact.setFiles(null);
                contact.setTasks(null);
                contact.setUser(null);
                contact.setTags(null);
            }

            List<Company> companyList = companyService.findCompanies();
            for (Company company : companyList) {
                company.setComments(null);
                company.setContacts(null);
                company.setDeals(null);
                company.setFiles(null);
                company.setTasks(null);
                company.setUser(null);
                company.setTags(null);
            }

            List<Subject> subjectList = new ArrayList<>();
            subjectList.addAll(companyList);
            subjectList.addAll(contactList);
            subjectList.addAll(dealList);
            model.addAttribute("subjectlist", subjectList);


        } catch (DataBaseException | ServiceException e) {
            LOGGER.error(e);
        }

    }
}