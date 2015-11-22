package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="newcompany", urlPatterns = "/new_company")
public class NewCompanyServlet extends HttpServlet{
    private DaoFactory dao;
    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
            createCompanyFromRequest(req);
            getServletContext().getRequestDispatcher("/new_contact").forward(req,resp);

        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCompanyFromRequest(HttpServletRequest request){
        try {
            Company result = new Company();
            GenericDao<Company> companyDao = dao.getDao(connection,Company.class);
            result.setName(request.getParameter("newcompanyname"));
            result.setPhoneNumber(request.getParameter("newcompanyphone"));
            result.setEmail(request.getParameter("newcompanyemail"));
            String url = request.getParameter("newcompanywebaddress");
            try {
                result.setWeb(new URL(url));
            } catch (MalformedURLException e) {
                result.setWeb(null);
            }
            result.setAdress(request.getParameter("newcompanyaddress"));
            companyDao.create(result);

        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

}
