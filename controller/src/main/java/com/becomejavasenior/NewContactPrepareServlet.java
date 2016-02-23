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
import java.util.List;

@WebServlet(name = "new_contact_prepare", urlPatterns = "/new_contact_prepare")
public class NewContactPrepareServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(NewContactPrepareServlet.class);
    @Autowired
    private DealService dealService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<Company> companyList = companyService.findCompanies();
            request.setAttribute("companylist", companyList);
            List<User> usersList = userService.getAllUsers();
            request.setAttribute("userslist", usersList);
            List<PhoneType> phoneTypes = contactService.getAllPhoneTypes();
            request.setAttribute("phonetypelist", phoneTypes);
            List<TaskType> taskTypes = taskService.getAllTaskTypes();
            request.setAttribute("tasktypes", taskTypes);
            List<DealStatus> dealStatuses = dealService.getAllDealStatuses();
            request.setAttribute("dealstatuses", dealStatuses);
            getServletContext().getRequestDispatcher("/jsp/newcontact.jsp").forward(request, response);
        } catch (DataBaseException e) {
            LOGGER.error("Error when prepearing data for newcontact.jsp", e);
        } catch (ServiceException e) {
            LOGGER.catching(e);
        }
    }
}
