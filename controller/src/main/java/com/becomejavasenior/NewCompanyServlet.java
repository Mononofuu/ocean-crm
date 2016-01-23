package com.becomejavasenior;

import com.becomejavasenior.impl.CompanyServiceImpl;
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
    private static final Logger LOGGER = LogManager.getLogger(NewCompanyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ajaxprocess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ajaxprocess(req, resp);
    }

    private void ajaxprocess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Company company = null;
        try {
            company = createCompanyFromRequest(req);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        mapper.writeValue(writer, company);
        resp.getWriter().write(writer.toString());
    }

    private Company createCompanyFromRequest(HttpServletRequest request) throws DataBaseException {
        Company result = new Company();
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
                LOGGER.error("Incorrect URL", e);
            }
        }
        result.setAdress(request.getParameter("newcompanyaddress"));
        CompanyService companyService = new CompanyServiceImpl();
        return companyService.saveCompany(result);
    }

}
