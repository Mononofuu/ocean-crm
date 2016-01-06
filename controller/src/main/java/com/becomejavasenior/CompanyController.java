package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * Created by Peter on 26.12.2015.
 */
@WebServlet("/companyedit")
public class CompanyController extends HttpServlet{
    private final static Logger logger = LogManager.getLogger(DealController.class);
    private DaoFactory dao;
    private Company company;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                try {
                    dao = new PostgreSqlDaoFactory();
                    GenericDao<Company> companyDao = dao.getDao(Company.class);
//                    int id = Integer.parseInt(request.getParameter("id"));
                    company = new Company();
//                    company.setId(id);
                    company.setName(request.getParameter("name"));
                    company.setPhoneNumber(request.getParameter("phoneNumber"));
                    company.setEmail(request.getParameter("email"));
                    company.setWeb(new URL(request.getParameter("web")));
                    company.setAdress(request.getParameter("address"));
                    companyDao.create(company);
                    logger.info("Company created:");
                    logger.info(company.getId());
                    logger.info(company.getName());
                    logger.info(company.getPhoneNumber());
                    logger.info(company.getEmail());
                    logger.info(company.getWeb());
                    logger.info(company.getAdress());
                } catch (DataBaseException e) {
                    logger.error("Error while creating company");
                    logger.catching(e);
                }
                break;
            case "update":
                try {
                    dao = new PostgreSqlDaoFactory();
                    GenericDao<Company> companyDao = dao.getDao(Company.class);
                    int id = Integer.parseInt(request.getParameter("id"));
                    company = (Company) companyDao.read(getId(request));
                    company.setName(request.getParameter("name"));
                    company.setPhoneNumber(request.getParameter("phoneNumber"));
                    company.setEmail(request.getParameter("email"));
                    company.setWeb(new URL(request.getParameter("web")));
                    company.setAdress(request.getParameter("address"));
                    companyDao.update(company);
                    logger.info("Company updated:");
                    logger.info(company.getId());
                    logger.info(company.getName());
                    logger.info(company.getPhoneNumber());
                    logger.info(company.getEmail());
                    logger.info(company.getWeb());
                    logger.info(company.getAdress());
                    request.getRequestDispatcher(request.getParameter("backurl")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while updating company");
                    logger.catching(e);
                }
                break;
            case "edit":
                try {
                    dao = new PostgreSqlDaoFactory();
                    GenericDao<Company> companyDao = dao.getDao(Company.class);
                    company = (Company) companyDao.read(getId(request));
                    request.setAttribute("company", company);
                    request.getRequestDispatcher("jsp/companyedit.jsp").forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while editing company");
                    logger.catching(e);
                }
                break;
            default:
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }


}
