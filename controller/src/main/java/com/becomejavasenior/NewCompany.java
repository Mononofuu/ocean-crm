package com.becomejavasenior;

import com.becomejavasenior.impl.SubjectTagDAOImpl;
import com.becomejavasenior.impl.TaskServiceImpl;
import com.becomejavasenior.impl.UserServiceImpl;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */

@WebServlet(urlPatterns = "/newcompany")
public class NewCompany extends HttpServlet {
    private final static String nextJSP = "/jsp/newcompany.jsp";
    private static TaskService taskService;
    private Logger logger = LogManager.getLogger(NewCompanyServlet.class);
    private DaoFactory dao;
    private Company createdCompany;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        String action = req.getParameter("action");
        logger.info("GET action = " + action);

        if (action == null || action.isEmpty()) {
            redirectTo(req, resp, nextJSP);
        } else {

            String json = null;
            try {
                switch (action) {
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
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        logger.debug(action);
        switch (action) {
            case "newcontact":
                newContact(req, resp);
                break;
            case "newcompany":
                newCompany(req, resp);
                break;
            default:
        }
    }

    private void newContact(HttpServletRequest request, HttpServletResponse response) {
        try {
            Contact contact = new Contact();

            Optional<String> contactName = Optional.ofNullable(request.getParameter("contactname"));
            contactName.ifPresent(contact::setName);

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

            Optional<String> webSite = Optional.ofNullable(request.getParameter("companysite"));
            webSite.ifPresent(site -> {
                try {
                    company.setWeb(new URL(site));
                } catch (MalformedURLException e) {
                    logger.catching(e);
                }
            });

            Optional<String> address = Optional.ofNullable(request.getParameter("companyaddress"));
            address.ifPresent(company::setAdress);

            Optional<String> responsible = Optional.ofNullable(request.getParameter("companyresp"));
            if (responsible.isPresent()) {
                GenericDao<User> userDao = dao.getDao(User.class);
                User mainContact = userDao.read(Integer.parseInt(responsible.get()));
                company.setUser(mainContact);
            }


            Optional<String> text = Optional.ofNullable(request.getParameter("companycomment"));
            if (text.isPresent()) {
                GenericDao<Comment> commentDao = dao.getDao(Comment.class);
                Comment comment = new Comment();
                comment.setText(text.get());
                comment.setDateCreated(new Timestamp(new Date().getTime()));
                comment.setSubject(company);
                User user = new User();
                user.setId(1);
                comment.setUser(user);
                logger.info(String.format("Trying to create comment: %s", comment.getText()));
                Comment createdComment = commentDao.create(comment);
                logger.info(String.format("Contact id = %d created", createdComment.getId()));
            }

            GenericDao<Company> companyDao = dao.getDao(Company.class);

            createdCompany = companyDao.create(company);


            logger.info("NEW COMPANY CREATED:");
            logger.info(createdCompany.getId());

            Optional<String[]> companyContacts = Optional.ofNullable(request.getParameterValues("contactlist[]"));
            if (companyContacts.isPresent()) {
                GenericDao<Contact> contactDao = dao.getDao(Contact.class);

                for (String contactId : companyContacts.get()) {
                    Contact contact = contactDao.read(Integer.parseInt(contactId));
                    contact.setCompany(createdCompany);
                    contactDao.update(contact);
                }
            }

            Optional<String[]> companyDeals = Optional.ofNullable(request.getParameterValues("addedDeals[]"));
            if (companyDeals.isPresent()) {
                GenericDao<Deal> dealDao = dao.getDao(Deal.class);
                GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
                for (String a : companyDeals.get()) {
                    String[] dealValues = a.split(";");
                    Deal deal = new Deal();
                    deal.setName(dealValues[0]);
                    DealStatus status = dealStatusDao.read(Integer.parseInt(dealValues[1]));
                    deal.setStatus(status);
                    deal.setBudget(Integer.parseInt(dealValues[2]));
                    deal.setDealCompany(createdCompany);
                    deal.setDateCreated(new Timestamp(new Date().getTime()));
                    GenericDao<Currency> currencyDao = dao.getDao(Currency.class);
                    Optional<Currency> dealCurrency = Optional.ofNullable(currencyDao.read(1)); //TODO
                    dealCurrency.ifPresent(deal::setCurrency);
                    dealDao.create(deal);
                }
            }

            Optional<String> companyTags = Optional.ofNullable(request.getParameter("companytags"));
            if (companyTags.isPresent()) {
                GenericDao<Tag> tagDAO = dao.getDao(Tag.class);
                SubjectTagDAOImpl subjectTagDAO = (SubjectTagDAOImpl) dao.getDao(SubjectTag.class);
                String[] tagArray = companyTags.get().split(" ");

                for (String tag : tagArray) {
                    Set<Tag> existedTags = subjectTagDAO.getAllTagsBySubjectId(createdCompany.getId());
                    Tag tagInstance = new Tag();
                    tagInstance.setName(tag);
                    if (existedTags.stream().filter(tag1 -> tag1.getName().equals(tag)).count() < 1) {
                        Tag returnedTag = tagDAO.create(tagInstance);
                        logger.info(String.format("Trying to create tag: %s", tag));
                        SubjectTag subjectTag = new SubjectTag();
                        subjectTag.setTag(returnedTag);
                        subjectTag.setSubject(createdCompany);
                        subjectTagDAO.create(subjectTag);
                    }
                }
            }

            Optional<String> addTask = Optional.ofNullable(request.getParameter("addTask"));
            if (addTask.isPresent() && addTask.get().equals("true")) {
                Task task = getTaskFromRequest(request);
                taskService = new TaskServiceImpl();
                taskService.saveTask(task);
            }
            logger.info("DONE");

        } catch (DataBaseException e) {
            logger.error("Error while creating new company");
            logger.catching(e);
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

    private Task getTaskFromRequest(HttpServletRequest request) throws DataBaseException {
        Task task = new Task();
        Optional<String> taskUser = Optional.ofNullable(request.getParameter("taskuser"));
        if (taskUser.isPresent()) {
            UserService userService = new UserServiceImpl();
            User user = userService.findUserById(Integer.parseInt(taskUser.get()));
            task.setUser(user);
        }
        task.setSubject(createdCompany);
        Optional<String> taskType = Optional.ofNullable(request.getParameter("tasktype"));
        taskType.ifPresent(s -> task.setType(TaskType.valueOf(s)));
        Optional<String> taskComment = Optional.ofNullable(request.getParameter("taskcomment"));
        taskComment.ifPresent(task::setComment);
        task.setDateCreated(new Date());
        Optional<String> taskDueDate = Optional.ofNullable(request.getParameter("taskduedate"));
        if (taskDueDate.isPresent() & !taskDueDate.get().equals("undefined")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            try {
                task.setDueTime(dateFormat.parse(taskDueDate.get()));
            } catch (ParseException e) {
                logger.catching(e);
            }
        } else {
            String period = request.getParameter("taskperiod");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            switch (period) {
                case "today":
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "allday":
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "tomorow":
                    c.add(Calendar.DAY_OF_MONTH, 2);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "nextweek":
                    c.set(Calendar.DAY_OF_WEEK, 0);
                    c.add(Calendar.DAY_OF_MONTH, 14);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "nextmonth":
                    c.set(Calendar.DAY_OF_MONTH, 0);
                    c.add(Calendar.MONTH, 2);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "nextyear":
                    c.set(Calendar.DAY_OF_MONTH, 0);
                    c.set(Calendar.MONTH, 0);
                    c.add(Calendar.YEAR, 2);
                    c.add(Calendar.MINUTE, -1);
                    break;
            }
            task.setDueTime(c.getTime());
        }
        return task;
    }

}
