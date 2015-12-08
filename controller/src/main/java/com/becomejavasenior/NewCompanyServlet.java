package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="newcompany", urlPatterns = "/new_company")
public class NewCompanyServlet extends HttpServlet{
    private Logger logger = LogManager.getLogger(NewCompanyServlet.class);
    private DaoFactory dao;

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
            req.setCharacterEncoding("UTF-8");
            createCompanyFromRequest(req);
            getServletContext().getRequestDispatcher("/new_contact_prepare").forward(req,resp);

        } catch (DataBaseException e) {
            logger.error("Error while quick adding new company", e);
        } catch (ServletException e) {
            logger.error("Error while quick adding new company", e);
        } catch (IOException e) {
            logger.error("Error while quick adding new company", e);
        }
    }

    private void createCompanyFromRequest(HttpServletRequest request){
        try {
            Company result = new Company();
            GenericDao<Company> companyDao = dao.getDao(Company.class);
            result.setName(request.getParameter("newcompanyname"));
            result.setPhoneNumber(request.getParameter("newcompanyphone"));
            result.setEmail(request.getParameter("newcompanyemail"));
            String url = request.getParameter("newcompanywebaddress");
            try {
                result.setWeb(new URL(url));
            } catch (MalformedURLException e) {
                result.setWeb(null);
                logger.warn("Incorrect URL", e);
            }
            result.setAdress(request.getParameter("newcompanyaddress"));
            companyDao.create(result);

        } catch (DataBaseException e) {
            logger.error("Error while quick adding new company", e);
        }
    }

}
