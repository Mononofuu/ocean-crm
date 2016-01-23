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

@WebServlet(name="new_contact_prepare", urlPatterns = "/new_contact_prepare")
public class NewContactPrepareServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(NewContactPrepareServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        try {
            List<Company> companyList = new CompanyServiceImpl().findCompanies();
            request.setAttribute("companylist", companyList);
            List<User> usersList = new UserServiceImpl().getAllUsers();
            request.setAttribute("userslist", usersList);
            List<PhoneType> phoneTypes = new ContactServiceImpl().getAllPhoneTypes();
            request.setAttribute("phonetypelist", phoneTypes);
            List<TaskType> taskTypes = new TaskServiceImpl().getAllTaskTypes();
            request.setAttribute("tasktypes", taskTypes);
            List<DealStatus> dealStatuses = new DealServiceImpl().getAllDealStatuses();
            request.setAttribute("dealstatuses", dealStatuses);
            getServletContext().getRequestDispatcher("/jsp/newcontact.jsp").forward(request,response);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for newcontact.jsp",e);
        }
    }
}
