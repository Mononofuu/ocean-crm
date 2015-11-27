package com.becomejavasenior.dashboard;

import com.becomejavasenior.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */
@WebServlet(name = "dashboardServlet")
public class DashboardServlet extends HttpServlet {

    private static DaoFactory myDaoFactory;
    private static Connection myConnection;

    private static final Map<String, DashboardFunction> SUPPORTED_FUNCTIONS;

    static {
        SUPPORTED_FUNCTIONS = new HashMap<>();
        addApiFunction(new DashboardAllDeals());
        addApiFunction(new DashBoardBudget());
        addApiFunction(new DashboardDealsWithTasks());
        addApiFunction(new DashboardDealsWithoutTasks());
        addApiFunction(new DashboardSuccessDeals());
        addApiFunction(new DashboardUnsuccessClosedDeals());
        addApiFunction(new DashboardInprogressTasks());
        addApiFunction(new DashboardFinishedTasks());
        addApiFunction(new DashboardOverdueTasks());
        addApiFunction(new DashboardContacts());
        addApiFunction(new DashboardCompanies());
        addApiFunction(new DashboardEvents());
    }


    public DashboardServlet() throws DataBaseException {
        myDaoFactory = new PostgreSqlDaoFactory();
        myConnection = myDaoFactory.getConnection();
    }

    private static void addApiFunction(DashboardFunction apiFunction) {
        SUPPORTED_FUNCTIONS.put(apiFunction.getName(), apiFunction);
    }

    protected Map<String, DashboardFunction> getSupportedFunction() {
        return SUPPORTED_FUNCTIONS;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        for (DashboardFunction command : getSupportedFunction().values()) {
            command.execute(request);
        }
        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    public interface DashboardFunction {

        String getName();

        void execute(HttpServletRequest request) throws IOException;
    }

    private static class DashboardAllDeals implements DashboardFunction {

        @Override
        public String getName() {
            return "getAllDeals";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            List deals = getAllDeals();
            request.setAttribute(getName(), String.valueOf(deals.size()));
        }
    }

    private static GenericDao getGenericDao(Class daoClass) {
        GenericDao dealGenericDao = null;
        try {
            dealGenericDao = myDaoFactory.getDao(myConnection, daoClass);
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return dealGenericDao;
    }

    private static class DashBoardBudget implements DashboardFunction {
        @Override
        public String getName() {
            return "getDealsBudget";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            List deals = getAllDeals();
            double dealsBudget = 0;
            assert deals != null;
            for (Object deal : deals) {
                if (deal instanceof Deal) {
                    dealsBudget = dealsBudget + ((Deal) deal).getBudget();
                }
            }
            request.setAttribute(getName(), dealsBudget);
        }
    }

    private static class DashboardDealsWithTasks implements DashboardFunction {
        @Override
        public String getName() {
            return "getDealWithTasks";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            List deals = getAllDeals();
            int dealsWithTasks = 0;
            for (Object deal : deals) {
                if (deal instanceof Deal) {
                    dealsWithTasks = dealsWithTasks + ((Deal) deal).getTasks().size();
                }
            }
            request.setAttribute(getName(), dealsWithTasks);
        }
    }

    private static List getAllDeals() {
        GenericDao genericDao = getGenericDao(Deal.class);
        List deals = null;
        try {
            deals = genericDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return deals;
    }

    private static class DashboardDealsWithoutTasks implements DashboardFunction {
        @Override
        public String getName() {
            return "getDealsWithoutTasks";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            List deals = getAllDeals();
            int dealsWithoutTasks = 0;
            for (Object deal : deals) {
                if (deal instanceof Deal) {
                    if (((Deal) deal).getTasks().size() == 0) {
                        dealsWithoutTasks++;
                    }
                }
            }
            request.setAttribute(getName(), dealsWithoutTasks);
        }
    }

    private static class DashboardSuccessDeals implements DashboardFunction {
        @Override
        public String getName() {
            return "successDeals";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            List deals = getAllDeals();
            int successDeals = 0;
            for (Object deal : deals) {
                if (deal instanceof Deal) {
                    if (((Deal) deal).getStatus().getName().equals("Успешно реализовано")) {
                        successDeals++;
                    }
                }
            }
            request.setAttribute(getName(), successDeals);
        }
    }

    private static class DashboardUnsuccessClosedDeals implements DashboardFunction {
        @Override
        public String getName() {
            return "unsuccessClosedDeals";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            List deals = getAllDeals();
            int unsuccessClosedDeals = 0;
            for (Object deal : deals) {
                if (deal instanceof Deal) {
                    if (((Deal) deal).getStatus().getName().equals("Закрыто и нереализовано")) {
                        unsuccessClosedDeals++;
                    }
                }
            }
            request.setAttribute(getName(), unsuccessClosedDeals);
        }
    }

    private static class DashboardInprogressTasks implements DashboardFunction {
        @Override
        public String getName() {
            return "tasksInProgress";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            GenericDao tasksDao = getGenericDao(Task.class);
            List tasks = null;
            try {
                tasks = tasksDao.readAll();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
            assert tasks != null;
            request.setAttribute(getName(), tasks.size());
        }
    }

    private static class DashboardFinishedTasks implements DashboardFunction {
        @Override
        public String getName() {
            return "finishedTasks";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            taskProcessingWithParameters(request, getName(), "Завершено");
        }
    }

    private static class DashboardOverdueTasks implements DashboardFunction {
        @Override
        public String getName() {
            return "overdueTasks";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            taskProcessingWithParameters(request, getName(), "Не завершено");
        }


    }

    private static void taskProcessingWithParameters(HttpServletRequest request, String name, String status) {
        GenericDao tasksDao = getGenericDao(Task.class);
        List tasks = null;
        try {
            tasks = tasksDao.readAll();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        int processingTasksCount = 0;
        assert tasks != null;
        for (Object task : tasks) {
            if (task instanceof Task) {
                if (((Task) task).getType().name().equals(status)) {
                    processingTasksCount++;
                }
            }
        }
        request.setAttribute(name, processingTasksCount);
    }

    private static class DashboardContacts implements DashboardFunction {
        @Override
        public String getName() {
            return "contacts";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            GenericDao contactsDao = getGenericDao(Contact.class);
            List contacts = null;
            try {
                contacts = contactsDao.readAll();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
            assert contacts != null;
            request.setAttribute(getName(), contacts.size());
        }
    }

    private static class DashboardCompanies implements DashboardFunction {
        @Override
        public String getName() {
            return "companies";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            GenericDao companiesDao = getGenericDao(Company.class);
            List companies = null;
            try {
                companies = companiesDao.readAll();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
            assert companies != null;
            request.setAttribute(getName(), companies.size());
        }
    }

    private static class DashboardEvents implements DashboardFunction {
        @Override
        public String getName() {
            return "events";
        }

        @Override
        public void execute(HttpServletRequest request) throws IOException {
            GenericDao eventsDao = getGenericDao(Event.class);
            List events = null;
            try {
                events = eventsDao.readAll();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
            request.setAttribute(getName(), events);
        }
    }
}
