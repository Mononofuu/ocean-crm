package com.becomejavasenior.dashboard;

import com.becomejavasenior.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
@WebServlet(name = "dashboardServlet")
public class DashboardServlet extends HttpServlet {

    private static DaoFactory myDaoFactory;

    public DashboardServlet() throws DataBaseException {
        myDaoFactory = new PostgreSqlDaoFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("allDeals", String.valueOf(getAllDeals().size()));
        request.setAttribute("dealsBudget", getDealsBudget());
        request.setAttribute("dealWithTasks", getDealWithTasks());
        request.setAttribute("dealsWithoutTasks", getDealsWithoutTasks());
        request.setAttribute("successDeals", getSuccessDeals());
        request.setAttribute("unsuccessClosedDeals", getUnsuccessClosedDeals());
        request.setAttribute("tasksInProgress", tasksInProgress());
        request.setAttribute("finishedTasks", getFinishedTasks());
        request.setAttribute("overdueTasks", getOverdueTasks());
        request.setAttribute("contacts", getContacts());
        request.setAttribute("companies", getCompanies());
        request.setAttribute("events", getEvents());
        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    private int getEvents() {
        GenericDao eventsDao = getGenericDao(Event.class);
        List events = null;
        try {
            events = eventsDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return events != null ? events.size() : 0;
    }

    private int getCompanies() {
        GenericDao companiesDao = getGenericDao(Company.class);
        List companies = null;
        try {
            companies = companiesDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return companies != null ? companies.size() : 0;
    }

    private int getContacts() {
        GenericDao contactsDao = getGenericDao(Contact.class);
        List contacts = null;
        try {
            contacts = contactsDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return contacts != null ? contacts.size() : 0;
    }

    private int tasksInProgress() {
        List tasks = getAllTasks();
        return tasks != null ? tasks.size() : 0;
    }

    private int getUnsuccessClosedDeals() {
        List deals = getAllDeals();
        int unsuccessClosedDeals = 0;
        for (Object deal : deals) {
            if (deal instanceof Deal) {
                if (((Deal) deal).getStatus().getName().equals("Закрыто и нереализовано")) {
                    unsuccessClosedDeals++;
                }
            }
        }
        return unsuccessClosedDeals;
    }

    private int getSuccessDeals() {
        List deals = getAllDeals();
        int successDeals = 0;
        for (Object deal : deals) {
            if (deal instanceof Deal) {
                if (((Deal) deal).getStatus().getName().equals("Успешно реализовано")) {
                    successDeals++;
                }
            }
        }
        return successDeals;
    }

    private int getDealsWithoutTasks() {
        List deals = getAllDeals();
        int dealsWithoutTasks = 0;
        for (Object deal : deals) {
            if (deal instanceof Deal) {
                if (((Deal) deal).getTasks().size() == 0) {
                    dealsWithoutTasks++;
                }
            }
        }
        return dealsWithoutTasks;
    }

    private double getDealsBudget() {
        List deals = getAllDeals();
        double dealsBudget = 0;
        assert deals != null;
        for (Object deal : deals) {
            if (deal instanceof Deal) {
                dealsBudget = dealsBudget + ((Deal) deal).getBudget();
            }
        }
        return dealsBudget;
    }

    private int getDealWithTasks() {
        List deals = getAllDeals();
        int dealsWithTasks = 0;
        for (Object deal : deals) {
            if (deal instanceof Deal) {
                dealsWithTasks = dealsWithTasks + ((Deal) deal).getTasks().size();
            }
        }
        return dealsWithTasks;
    }

    private GenericDao getGenericDao(Class daoClass) {
        GenericDao dealGenericDao = null;
        try {
            dealGenericDao = myDaoFactory.getDao(daoClass);
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return dealGenericDao;
    }

    private List getAllDeals() {
        GenericDao genericDao = getGenericDao(Deal.class);
        List deals = null;
        try {
            deals = genericDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return deals;
    }

    private int getFinishedTasks() {
        List tasks = getAllTasks();
        int completedTasks = 0;
        assert tasks != null;
        for (Object task : tasks) {
            if (task instanceof Task) {
                if (((Task) task).getType().name().equals("Completed")) {
                    completedTasks++;
                }
            }
        }
        return completedTasks;
    }

    private int getOverdueTasks() {
        List tasks = getAllTasks();
        int overdueTasks = 0;
        assert tasks != null;
        for (Object task : tasks) {
            if (task instanceof Task) {
                if (((Task) task).getType().name().equals("Not completed")) {
                    overdueTasks++;
                }
            }
        }
        return overdueTasks;
    }

    private List getAllTasks() {
        GenericDao tasksDao = getGenericDao(Task.class);
        List tasks = null;
        try {
            tasks = tasksDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}
