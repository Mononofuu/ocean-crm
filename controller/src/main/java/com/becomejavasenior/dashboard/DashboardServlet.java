package com.becomejavasenior.dashboard;

import com.becomejavasenior.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */
@WebServlet(name = "dashboardServlet")
public class DashboardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, Object> result = new DashboardServiceImpl().getDasboardInformation();
        for (Map.Entry entry: result.entrySet()) {
            request.setAttribute((String) entry.getKey(), entry.getValue());
        }

        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

}
