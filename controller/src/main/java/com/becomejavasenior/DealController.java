package com.becomejavasenior;

import com.becomejavasenior.impl.DealContactDAOImpl;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DealController extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealController.class);
    private final static String nextJSP = "/jsp/newdeal.jsp";
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
            //TODO

        } catch (DataBaseException e) {
            logger.error("Error while creating new task");
            logger.catching(e);
        }
    }

    private void newContact(HttpServletRequest request, HttpServletResponse response) {
        try {
            Contact contact = new Contact();

            Optional<String> contactName = Optional.ofNullable(request.getParameter("contactname"));
            contactName.ifPresent(contact::setName);

            Optional<String> contactCompany = Optional.ofNullable(request.getParameter("contactcompany"));
            if (contactCompany.isPresent()) {
                GenericDao<Company> companyDao = dao.getDao(Company.class);
                Company company = companyDao.read(Integer.parseInt(contactCompany.get()));
                contact.setCompany(company);
            }

            Optional<String> contactPosition = Optional.ofNullable(request.getParameter("contactposition"));
            contactPosition.ifPresent(contact::setPost);

            Optional<String> contactPhoneType = Optional.ofNullable(request.getParameter("contactphonetype"));
            contactPhoneType.ifPresent(s -> contact.setPhoneType(PhoneType.valueOf(contactPhoneType.get())));

            Optional<String> contactPhoneNumber = Optional.ofNullable(request.getParameter("contactphonenumber"));
            contactPhoneNumber.ifPresent(contact::setPhone);

            Optional<String> contactEmail = Optional.ofNullable(request.getParameter("contactemail"));
            contactEmail.ifPresent(contact::setEmail);

            Optional<String> contactSkype = Optional.ofNullable(request.getParameter("contactskype"));
            contactSkype.ifPresent(contact::setSkype);

            GenericDao<Contact> contactDao = dao.getDao(Contact.class);
            Contact createdContact = contactDao.create(contact);

            logger.info("NEW CONTACT CREATED:");
            logger.info(createdContact.getId());

        } catch (DataBaseException e) {
            logger.error("Error while creating new contact");
            logger.catching(e);
        }
    }

    private void newCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Company company = new Company();
            Optional<String> companyName = Optional.ofNullable(request.getParameter("companyname"));
            companyName.ifPresent(company::setName);

            Optional<String> companyPhone = Optional.ofNullable(request.getParameter("companyphone"));
            companyPhone.ifPresent(company::setPhoneNumber);

            Optional<String> companyEmail = Optional.ofNullable(request.getParameter("companyemail"));
            companyEmail.ifPresent(company::setEmail);

            Optional<String> companySite = Optional.ofNullable(request.getParameter("companysite"));
            companySite.ifPresent(s -> {
                try {
                    company.setWeb(new URL(s));
                } catch (MalformedURLException e) {
                    logger.catching(e);
                }
            });

            Optional<String> companyAddress = Optional.ofNullable(request.getParameter("companyaddress"));
            companyAddress.ifPresent(company::setAdress);

            GenericDao<Company> companyDao = dao.getDao(Company.class);
            Company createdCompany = companyDao.create(company);

            logger.info("NEW COMPANY CREATED:");
            logger.info(createdCompany.getId());
        } catch (DataBaseException e) {
            logger.error("Error while creating new company");
            logger.catching(e);
        }
    }

    private void newDeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Deal deal = new Deal();

            Optional<String> dealName = Optional.ofNullable(request.getParameter("dealname"));
            dealName.ifPresent(deal::setName);

            Optional<String> dealResp = Optional.ofNullable(request.getParameter("dealresp"));
            if (dealResp.isPresent()) {
                GenericDao<User> userDao = dao.getDao(User.class);
                User mainContact = userDao.read(Integer.parseInt(dealResp.get()));
                deal.setMainContact(mainContact);
            }

            Optional<String> dealBudget = Optional.ofNullable(request.getParameter("dealbudget"));
            dealBudget.ifPresent(s -> deal.setBudget(Integer.parseInt(s)));

            Optional<String> dealStatus = Optional.ofNullable(request.getParameter("dealstatus"));
            if (dealStatus.isPresent()) {
                GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
                DealStatus status = dealStatusDao.read(Integer.parseInt(dealStatus.get()));
                deal.setStatus(status);
            }

            Optional<String> dealCompany = Optional.ofNullable(request.getParameter("dealcompany"));
            if (dealCompany.isPresent()) {
                GenericDao<Company> companyDao = dao.getDao(Company.class);
                deal.setDealCompany(companyDao.read(Integer.parseInt(dealCompany.get())));
            }

            GenericDao<Currency> currencyDao = dao.getDao(Currency.class);
            Optional<Currency> dealCurrency = Optional.ofNullable(currencyDao.read(1)); //TODO
            dealCurrency.ifPresent(deal::setCurrency);

            deal.setDateWhenDealClose(null);

            Optional<String> dealCreatedDate = Optional.ofNullable(request.getParameter("dealcreated"));
            if (dealCreatedDate.isPresent()) {
                logger.debug(dealCreatedDate.get());
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dealCreatedDate.get());
                deal.setDateCreated(new Timestamp(date.getTime()));
            }

            GenericDao<Deal> dealDao = dao.getDao(Deal.class);
            logger.info("TRYING TO CREATE DEAL");
            createdDeal = dealDao.create(deal);
            logger.info("DEAL CREATED");

            GenericDao<Subject> subjectDao = dao.getDao(Subject.class);
            Subject subject = subjectDao.read(createdDeal.getId());

            Optional<String[]> contactList = Optional.ofNullable(request.getParameterValues("dealcontactlist[]"));
            if (contactList.isPresent()) {
                DealContactDAOImpl dealContactDAO = new DealContactDAOImpl();
                GenericDao<Contact> contactDao = dao.getDao(Contact.class);
                List<Contact> dealContacts = new ArrayList<>();

                for (String contactId : contactList.get()) {
                    Contact contact = contactDao.read(Integer.parseInt(contactId));
                    DealContact dealContact = new DealContact();
                    dealContact.setDeal(createdDeal);
                    dealContact.setContact(contact);
                    dealContactDAO.create(dealContact);
                    dealContacts.add(contact);
                }
                deal.setContacts(dealContacts);
            }

            Optional<String> dealTags = Optional.ofNullable(request.getParameter("dealtags"));
            if (dealTags.isPresent()) {
                GenericDao<Tag> tagDAO = dao.getDao(Tag.class);
                GenericDao<SubjectTag> subjectTagDAO = dao.getDao(SubjectTag.class);
                String[] tagArray = dealTags.get().split(" ");

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
            }

            Optional<String> dealComment = Optional.ofNullable(request.getParameter("dealcomment"));
            if (dealComment.isPresent()) {
                GenericDao<Comment> commentDao = dao.getDao(Comment.class);
                Comment comment = new Comment();
                comment.setText(dealComment.get());
                comment.setDateCreated(new Timestamp(new Date().getTime()));
                comment.setSubject(subject);
                User user = new User();
                user.setId(1);
                comment.setUser(user);
                logger.info(String.format("Trying to create comment: %s", comment.getText()));
                Comment createdComment = commentDao.create(comment);
                logger.info(String.format("Contact id = %d created", createdComment.getId()));
            }

        } catch (DataBaseException e) {
            logger.error("Error while creating new deal");
            logger.catching(e);
        } catch (ParseException e) {
            logger.catching(e);
        }

        response.getWriter().println("DEAL CREATED SUCCESSFULLY");
        logger.info("Deal created successfully");
        logger.info(String.format("Deal id = %d", createdDeal.getId()));
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

        if (action == null || action.isEmpty()) {
            redirectTo(request, response, nextJSP);
        } else {
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
                        redirectTo(request, response, nextJSP);
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

    private void redirectTo(HttpServletRequest request, HttpServletResponse response, String page) {
        try {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            logger.info(String.format("REDIRECTING TO %s", nextJSP));
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.catching(e);
        }
    }
}
