package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name = "newcompany", urlPatterns = "/new_company")
public class NewCompanyServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(NewCompanyServlet.class);
    private DaoFactory dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ajaxprocess(req, resp);
    }

    private void ajaxprocess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error(e);
        }
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Company company = null;
        try {
            company = createCompanyFromRequest(req);
        } catch (DataBaseException e) {
            logger.error(e);
        }
        mapper.writeValue(writer, company);
        resp.getWriter().write(writer.toString());
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        try {
            dao = new PostgreSqlDaoFactory();
            req.setCharacterEncoding("UTF-8");
            createCompanyFromRequest(req);
            getServletContext().getRequestDispatcher("/new_contact_prepare").forward(req, resp);

        } catch (DataBaseException e) {
            logger.error("Error while quick adding new company", e);
        } catch (ServletException e) {
            logger.error("Error while quick adding new company", e);
        } catch (IOException e) {
            logger.error("Error while quick adding new company", e);
        }
    }

    private Company createCompanyFromRequest(HttpServletRequest request) throws DataBaseException {
        Company result = new Company();
        GenericDao<Company> companyDao = dao.getDao(Company.class);
        result.setName(request.getParameter("newcompanyname"));
        result.setPhoneNumber(request.getParameter("newcompanyphone"));
        result.setEmail(request.getParameter("newcompanyemail"));
        String url = request.getParameter("newcompanywebaddress");
        try {
            result.setWeb(new URL(url));
        } catch (MalformedURLException e) {
            try {
                result.setWeb(new URL("http://"+url));
            } catch (MalformedURLException e1) {
                logger.error("Incorrect URL", e);
            }
        }
        result.setAdress(request.getParameter("newcompanyaddress"));
        return companyDao.create(result);


    }

}
