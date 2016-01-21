package com.becomejavasenior.dashboard;

import com.becomejavasenior.*;
import com.becomejavasenior.impl.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Lybachevskiy.Vladislav
 */
@WebServlet(name = "dashboardServlet")
public class DashboardServlet extends HttpServlet {

    private ApplicationContext context;
    private DealTemplateDAOImpl dealDAO;
    private TaskTemplateDAOImpl taskDAO;
    private ContactTemplateDAOImpl contactDAO;
    private CompanyTemplateDAOImpl companyDAO;
    private EventTemplateDAOImpl eventDAO;

    public DashboardServlet() throws DataBaseException {
        context = new ClassPathXmlApplicationContext("spring-datasource.xml");
        dealDAO = (DealTemplateDAOImpl) context.getBean("dealDAO");
        taskDAO = (TaskTemplateDAOImpl) context.getBean("taskDAO");
        contactDAO = (ContactTemplateDAOImpl) context.getBean("contactDAO");
        companyDAO = (CompanyTemplateDAOImpl) context.getBean("companyDAO");
        eventDAO = (EventTemplateDAOImpl) context.getBean("eventDAO");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("allDeals", dealDAO.findTotalDeals());
        request.setAttribute("dealsBudget", dealDAO.findTotalDealsBudget());
        request.setAttribute("dealWithTasks", dealDAO.findTotalDealsWithTasks());
        request.setAttribute("dealsWithoutTasks", dealDAO.findTotalDealsWithoutTasks());
        request.setAttribute("successDeals", dealDAO.findTotalSuccessDeals());
        request.setAttribute("unsuccessClosedDeals", dealDAO.findTotalUnsuccessClosedDeals());
        request.setAttribute("tasksInProgress", taskDAO.findTotalTasksInProgress());
        request.setAttribute("finishedTasks", taskDAO.findTotalFinishedTasks());
        request.setAttribute("overdueTasks", taskDAO.findTotalOverdueTasks());
        request.setAttribute("contacts", contactDAO.findTotalContacts());
        request.setAttribute("companies", companyDAO.findTotalCompanies());
        request.setAttribute("events", eventDAO.readLastEvents());
        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

}
