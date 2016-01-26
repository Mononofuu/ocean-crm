package com.becomejavasenior.dashboard;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.impl.*;
import com.becomejavasenior.interfacedao.CompanyDAO;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger LOGGER = LogManager.getLogger(DashboardServlet.class);

    private ApplicationContext context;
    private DealTemplateDAOImpl dealDAO;
    private TaskTemplateDAOImpl taskDAO;
    private ContactDAO contactDAO;
    private CompanyDAO companyDAO;
    private EventTemplateDAOImpl eventDAO;

    public DashboardServlet() throws DataBaseException {
        context = new ClassPathXmlApplicationContext("spring-datasource.xml");
        dealDAO = (DealTemplateDAOImpl) context.getBean("dealDAO");
        taskDAO = (TaskTemplateDAOImpl) context.getBean("taskDAO");
        contactDAO = (ContactDAO) context.getBean("contactDAO");
        companyDAO = (CompanyDAO) context.getBean("companyDAO");
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
        try {
            request.setAttribute("contacts", contactDAO.findTotalEntryes());
            request.setAttribute("companies", companyDAO.findTotalEntryes());
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        request.setAttribute("events", eventDAO.readLastEvents());
        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

}
