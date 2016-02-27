package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import com.becomejavasenior.validation.TaskFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(taskFormValidator);
    }

    @RequestMapping(value = "/newtask", method = RequestMethod.GET)
    public String displayNewTask(Model model) {

        try {
            List<User> usersList = userService.getAllUsers();

            for (User user: usersList){
                /*
                user.setComments(new ArrayList<>());
                user.setEvents(new ArrayList<>());
                user.setFiles(new ArrayList<>());
                user.setRoles(new HashSet<>());
                user.setTasks(new ArrayList<>());
                */
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
            model.addAttribute("deallist", dealList);
            List<Contact> contactList = contactService.findContacts();

            for (Contact contact: contactList){
                contact.setComments(null);
                contact.setDeals(null);
                contact.setFiles(null);
                contact.setTasks(null);
            }

            model.addAttribute("contactlist", contactList);

            List<Company> companyList = companyService.findCompanies();
            model.addAttribute("companylist", companyList);
            model.addAttribute("taskForm", new Task());
        } catch (DataBaseException | ServiceException e) {
            LOGGER.error(e);
        }

        return "taskform";

    }

    @RequestMapping(value = "/newtask", method = RequestMethod.POST)
    public String saveOrUpdateTask(@ModelAttribute("taskForm") @Validated Task task,
                                   BindingResult result, Model model,
                                   final RedirectAttributes redirectAttributes) throws ServiceException {
        if (result.hasErrors()) {
            LOGGER.debug(result.toString());
            return "taskform";
        }
        try {
            taskService.saveTask(task);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        return "tasklist";
//        }
    }

}