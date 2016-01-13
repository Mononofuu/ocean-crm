package com.becomejavasenior;

import com.becomejavasenior.impl.CompanyServiceImpl;
import com.becomejavasenior.impl.ContactServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private DaoFactory dao;
    private Contact contact;

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
//                    GenericDao<Contact> contactDao = dao.getDao(Contact.class);
                    ContactService contactService = new ContactServiceImpl();
                    contact = new Contact();
                    contact.setName(request.getParameter("name"));
                    GenericDao<Company> companyDao = dao.getDao(Company.class);
                    Company company = companyDao.read(Integer.parseInt(request.getParameter("companyid")));
                    contact.setCompany(company);
                    contact.setPost(request.getParameter("post"));
                    contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype")));
                    contact.setPhone(request.getParameter("contactphonenumber"));
                    contact.setEmail(request.getParameter("contactemail"));
                    contact.setSkype(request.getParameter("contactskype"));
//                    Contact createdContact = contactDao.create(contact);
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
                    dao = new PostgreSqlDaoFactory();
//                    GenericDao<Contact> contactDao = dao.getDao(Contact.class);
                    ContactService contactService = new ContactServiceImpl();
                    int id = Integer.parseInt(request.getParameter("id"));
                    contact = new Contact();
                    contact.setId(id);
                    contact.setName(request.getParameter("name"));
//                    GenericDao<Company> companyDao = dao.getDao(Company.class);
                    CompanyService companyService = new CompanyServiceImpl();
//                    Company company = companyDao.read(Integer.parseInt(request.getParameter("companyid")));
                    Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("companyid")));
                    contact.setCompany(company);
                    contact.setPost(request.getParameter("contactpost"));
                    contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype")));
                    contact.setPhone(request.getParameter("contactphone"));
                    contact.setEmail(request.getParameter("contactemail"));
                    contact.setSkype(request.getParameter("contactskype"));
//                    contactDao.update(contact);
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
                    dao = new PostgreSqlDaoFactory();
//                    GenericDao<Contact> contactDao = dao.getDao(Contact.class);
                    ContactService contactService = new ContactServiceImpl();
//                    contact = (Contact) contactDao.read(getId(request));
                    contact = contactService.findContactById(getId(request));
                    request.setAttribute("contact", contact);
//                    GenericDao companyDao = dao.getDao(Company.class);
                    CompanyService companyService = new CompanyServiceImpl();
//                    List<Company> companyList = companyDao.readAll();
                    List<Company> companyList = companyService.findCompaniesLite();
                    request.setAttribute("companies", companyList);
                    GenericDao userDao = dao.getDao(User.class);
                    List<User> userList = userDao.readAll();
                    request.setAttribute("users", userList);
                    GenericDao phoneTypeDao = dao.getDao(PhoneType.class);
                    List<PhoneType> phoneTypeList = phoneTypeDao.readAll();
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
