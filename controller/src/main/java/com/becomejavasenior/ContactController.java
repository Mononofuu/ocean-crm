package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by kramar on 30.12.15.
 */
@WebServlet("/contactedit")
public class ContactController extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

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
                Contact contact;
                try {
                    contact = new Contact();
                    contact.setName(request.getParameter("name"));
                    Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("companyid")));
                    contact.setCompany(company);
                    contact.setPost(request.getParameter("post"));
                    contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype")));
                    contact.setPhone(request.getParameter("contactphonenumber"));
                    contact.setEmail(request.getParameter("contactemail"));
                    contact.setSkype(request.getParameter("contactskype"));
                    contactService.saveContact(contact);
                    logger.info("Contact created:");
                    logger.info(contact.getId());
                    logger.info(contact.getName());
                    logger.info(contact.getCompany().toString());
                    logger.info(contact.getPost());
                    logger.info(contact.getPhone());
                    logger.info(contact.getPhoneType());
                    logger.info(contact.getUser());
                    logger.info(contact.getEmail());
                    logger.info(contact.getSkype());
                } catch (DataBaseException e) {
                    logger.error("Error while creating contact");
                    logger.catching(e);
                }
                break;
            case "update":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    contact = new Contact();
                    contact.setId(id);
                    contact.setName(request.getParameter("name"));
                    Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("companyid")));
                    contact.setCompany(company);
                    contact.setPost(request.getParameter("contactpost"));
                    contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype")));
                    contact.setPhone(request.getParameter("contactphone"));
                    contact.setEmail(request.getParameter("contactemail"));
                    contact.setSkype(request.getParameter("contactskype"));
                    contactService.saveContact(contact);
                    logger.info("Contact updated:");
                    logger.info(contact.getId());
                    logger.info(contact.getName());
                    logger.info(contact.getCompany().toString());
                    logger.info(contact.getPost());
                    logger.info(contact.getPhone());
                    logger.info(contact.getPhoneType());
                    logger.info(contact.getUser());
                    logger.info(contact.getEmail());
                    logger.info(contact.getSkype());
                    request.getRequestDispatcher(request.getParameter("backurl")).forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while updating contact");
                    logger.catching(e);
                }
                break;
            case "edit":
                try {
                    contact = contactService.findContactById(getId(request));
                    request.setAttribute("contact", contact);
                    List<Company> companyList = companyService.findCompaniesLite();
                    request.setAttribute("companies", companyList);

                    List<User> userList = userService.getAllUsers();
                    request.setAttribute("users", userList);
                    List<PhoneType> phoneTypeList = contactService.getAllPhoneTypes();
                    request.setAttribute("phonetypes", phoneTypeList);
                    request.getRequestDispatcher("jsp/contactedit.jsp").forward(request, response);
                } catch (DataBaseException e) {
                    logger.error("Error while editing contact");
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
