package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kramar on 19.11.15.
 */
@WebServlet("/dealslist")
public class DealsListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("name", "Peter");

        ArrayList<Deal> itemList = new ArrayList<Deal>();

        Deal item = new Deal();
        item.setName("Сделка 1");
        item.setBudget(100);
        itemList.add(item);

        item = new Deal();
        item.setName("Сделка 2");
        item.setBudget(200);
        itemList.add(item);

        item = new Deal();
        item.setName("Сделка 3");
        item.setBudget(300);
        itemList.add(item);


        resp.setContentType("text/html");
        req.setAttribute("test", "My test");
        req.setAttribute("itemList", itemList);
        req.getRequestDispatcher("dealslist.jsp").forward(req, resp);

    }
}



