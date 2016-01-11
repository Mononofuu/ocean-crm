package com.becomejavasenior;

import com.becomejavasenior.impl.DealContactDAOImpl;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DealController extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealController.class);
    private DaoFactory dao;
    private Deal createdDeal;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        logger.debug(action);
        switch (action) {
            case "newdeal":
                newDeal(request, response);
                break;
            case "newcontact":
                newContact(request, response);
                break;
            case "newcompany":
                newCompany(request, response);
                break;
            case "newtask":
                newTask(request, response);
                break;
            default:
        }

    }

    private void newTask(HttpServletRequest request, HttpServletResponse response) {
        try {
            Task task = new Task();
            GenericDao<Task> taskDao = dao.getDao(Task.class);

        } catch (DataBaseException e) {
            logger.error("Error while creating new contact");
            logger.catching(e);
        }
    }

    private void newContact(HttpServletRequest request, HttpServletResponse response) {
        try {
            Contact contact = new Contact();
            contact.setName(request.getParameter("contactname"));
            GenericDao<Company> companyDao = dao.getDao(Company.class);
            Company company = companyDao.read(Integer.parseInt(request.getParameter("contactcompany")));
            contact.setCompany(company);
            contact.setPost(request.getParameter("contactposition"));
            contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype")));
            contact.setPhone(request.getParameter("contactphonenumber"));
            contact.setEmail(request.getParameter("contactemail"));
            contact.setSkype(request.getParameter("contactskype"));
            GenericDao<Contact> contactDao = dao.getDao(Contact.class);
            Contact createdContact = contactDao.create(contact);

            logger.info("NEW CONTACT CREATED:");
            logger.info(createdContact.getId());
            logger.info(createdContact.getName());
            logger.info(createdContact.getCompany().getName());
            logger.info(createdContact.getPost());
            logger.info(createdContact.getPhoneType());
            logger.info(createdContact.getPhone());
            logger.info(createdContact.getEmail());
            logger.info(createdContact.getSkype());

        } catch (DataBaseException e) {
            logger.error("Error while creating new contact");
            logger.catching(e);
        }
    }

    private void newCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Company company = new Company();
            company.setName(request.getParameter("companyname"));
            company.setPhoneNumber(request.getParameter("companyphone"));
            company.setEmail(request.getParameter("companyemail"));
            company.setWeb(new URL(request.getParameter("companysite")));
            company.setAdress(request.getParameter("companyaddress"));

            GenericDao<Company> companyDao = dao.getDao(Company.class);

            Company createdCompany = companyDao.create(company);

            logger.info("NEW COMPANY CREATED:");
            logger.info(createdCompany.getId());
            logger.info(createdCompany.getName());
            logger.info(createdCompany.getPhoneNumber());
            logger.info(createdCompany.getEmail());
            logger.info(createdCompany.getWeb());
            logger.info(createdCompany.getAdress());
            logger.info(createdCompany.getComments());


        } catch (DataBaseException e) {
            logger.error("Error while creating new company");
            logger.catching(e);
        }
    }

    private void newDeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Comment createdComment = null;
        try {
            Map<String, String[]> map = request.getParameterMap();
//            for (Map.Entry<String, String[]> entry : map.entrySet()) {
//                logger.debug("^^^^^^^^^");
//                logger.debug(entry.getKey());
//                for (String s : entry.getValue()) {
//                    logger.debug(s);
//                }
//                logger.debug("###########   ");
//            }

            Deal deal = new Deal();
            deal.setName(request.getParameter("dealname"));
            String[] contactList = request.getParameterValues("dealcontactlist[]");
            GenericDao<User> userDao = dao.getDao(User.class);
            User mainContact = userDao.read(Integer.parseInt(request.getParameter("dealresp")));
            deal.setMainContact(mainContact);
            deal.setBudget(Integer.parseInt(request.getParameter("dealbudget")));
            GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
            DealStatus status = dealStatusDao.read(Integer.parseInt(request.getParameter("tasktype")));
            deal.setStatus(status);
            GenericDao<Company> companyDao = dao.getDao(Company.class);
            deal.setDealCompany(companyDao.read(Integer.parseInt(request.getParameter("dealcompany"))));
            GenericDao<Currency> currencyDao = dao.getDao(Currency.class);
            deal.setCurrency(currencyDao.read(1));//TODO
            deal.setDateWhenDealClose(null);
            deal.setDateCreated(new Timestamp(new Date().getTime()));

            GenericDao<Deal> dealDao = dao.getDao(Deal.class);
            logger.info("TRYING TO CREATE DEAL");
            createdDeal = dealDao.create(deal);
            logger.info("DEAL CREATED");


            DealContactDAOImpl dealContactDAO = new DealContactDAOImpl();
            GenericDao<Contact> contactDao = dao.getDao(Contact.class);
            List<Contact> dealContacts = new ArrayList<>();
            for (String contactId : contactList) {
                Contact contact = contactDao.read(Integer.parseInt(contactId));
                DealContact dealContact = new DealContact();
                dealContact.setDeal(createdDeal);//TODO
                dealContact.setContact(contact);
                dealContactDAO.create(dealContact);
                dealContacts.add(contact);
            }
            deal.setContacts(dealContacts);


            GenericDao<Tag> tagDAO = dao.getDao(Tag.class);
            GenericDao<SubjectTag> subjectTagDAO = dao.getDao(SubjectTag.class);
            GenericDao<Subject> subjectDao = dao.getDao(Subject.class);
            String tags = request.getParameter("dealtags");
            String[] tagArray = tags.split(" ");
            Subject subject = subjectDao.read(createdDeal.getId());



            for (String tag : tagArray) {
                Tag tagInstance = new Tag();
                tagInstance.setName(tag);
                Tag returnedTag = tagDAO.create(tagInstance);
                logger.info(String.format("Trying to create tag: %s", tag));
                SubjectTag subjectTag = new SubjectTag();
                subjectTag.setTag(returnedTag);
                subjectTag.setSubject(subject);
                subjectTagDAO.create(subjectTag);
            }

            GenericDao<Comment> commentDao = dao.getDao(Comment.class);
            Comment comment = new Comment();
            comment.setText(request.getParameter("dealcomment"));
            comment.setDateCreated(new Timestamp(new Date().getTime()));
            comment.setSubject(subject);
            User user = new User();
            user.setId(1);
            comment.setUser(user);
            logger.info(String.format("Trying to create comment: %s", comment.getText()));
            createdComment = commentDao.create(comment);


        } catch (DataBaseException e) {
            logger.error("Error while creating new deal");
            logger.catching(e);
        }


        response.getWriter().println("DEAL CREATED SUCCESSFULLY");
        logger.info("Deal created successfully");
        logger.info(String.format("Deal id = %d", createdDeal.getId()));
        logger.info(String.format("Deal name: %s", request.getParameter("dealname")));
        logger.info(String.format("Deal phase: %s", request.getParameter("dealphase")));
        logger.info(String.format("Deal budget: %s", request.getParameter("dealbudget")));
        logger.info(String.format("Deal responsible: %s", request.getParameter("dealresp")));
        logger.info(String.format("Deal company: %s", request.getParameter("dealcompany")));
        logger.info(String.format("Comment id = %d", createdComment.getId()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        String action = request.getParameter("action");
        logger.info("GET action = " + action);

        String json = null;
        try {
            switch (action) {
                case "getCompanies":
                    GenericDao<Company> companyDao = dao.getDao(Company.class);
                    List<Company> companyList = companyDao.readAll();
                    json = new Gson().toJson(companyList);
                    break;
                case "getContacts":
                    GenericDao<Contact> contactDao = dao.getDao(Contact.class);
                    List<Contact> contactList = contactDao.readAll();
                    json = new Gson().toJson(contactList);
                    break;
                case "getDealStatuses":
                    GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
                    List<DealStatus> dealStatusList = dealStatusDao.readAll();
                    json = new Gson().toJson(dealStatusList);
                    break;
                case "getPhoneTypes":
                    json = new Gson().toJson(PhoneType.values());
                    break;
                case "getTaskTypes":
                    json = new Gson().toJson(TaskType.values());
                    break;
                case "getUsers":
                    GenericDao<User> userDao = dao.getDao(User.class);
                    List<User> users = userDao.readAll();
                    json = new Gson().toJson(users);
                    break;
                default:
            }
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
