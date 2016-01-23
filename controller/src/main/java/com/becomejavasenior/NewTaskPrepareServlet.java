package com.becomejavasenior;

import com.becomejavasenior.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="new_task_prepare", urlPatterns = "/new_task_prepare")
public class NewTaskPrepareServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(NewTaskPrepareServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        try {
            TaskService taskService = new TaskServiceImpl();
            UserService userService = new UserServiceImpl();
            DealService dealService = new DealServiceImpl();
            ContactService contactService = new ContactServiceImpl();
            CompanyService companyService = new CompanyServiceImpl();
            List<User> usersList = userService.getAllUsers();
            req.setAttribute("userslist", usersList);
            List<TaskType> taskTypeList = taskService.getAllTaskTypes();
            req.setAttribute("tasktypes", taskTypeList);
            List<Deal> dealList = dealService.findDeals();
            req.setAttribute("deallist", dealList);
            List<Contact> contactList = contactService.findContacts();
            req.setAttribute("contactlist", contactList);
            List<Company> companyList = companyService.findCompanies();
            req.setAttribute("companylist", companyList);
            getServletContext().getRequestDispatcher("/jsp/newtask.jsp").forward(req,resp);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
    }
}
