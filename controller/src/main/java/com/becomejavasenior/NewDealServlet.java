package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kramar on 16.11.15.
 */

public class NewDealServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setAttribute("test", "My parameter");
        req.getRequestDispatcher("newdeal.jsp").forward(req, resp);
    }
}
