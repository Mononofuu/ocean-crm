package com.becomejavasenior;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */

@WebServlet(urlPatterns = "/newcompany")
public class NewCompany extends HttpServlet {
    private static final String nextJSP = "/jsp/newcompany.jsp";
    private static Logger logger = LogManager.getLogger(NewCompanyServlet.class);
    @Autowired
    private DealService dealService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TaskService taskService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        logger.info("GET action = " + action);

        if (action == null || action.isEmpty()) {
            redirectTo(req, resp, nextJSP);
        } else {

            String json = null;
            try {
                switch (action) {
                    case "getContacts":
                        List<Contact> contactList = contactService.findContactsLite();
                        json = new Gson().toJson(contactList);
                        break;
                    case "getDealStatuses":
                        List<DealStatus> dealStatusList = dealService.getAllDealStatuses();
                        json = new Gson().toJson(dealStatusList);
                        break;
                    case "getPhoneTypes":
                        json = new Gson().toJson(PhoneType.values());
                        break;
                    case "getTaskTypes":
                        json = new Gson().toJson(TaskType.values());
                        break;
                    case "getUsers":
                        List<User> users = userService.getAllUsersLite();
                        json = new Gson().toJson(users);
                        break;
                    default:
                }
            } catch (DataBaseException e) {
                logger.error("Error while getting Service");
                logger.catching(e);
            } catch (ServiceException e) {
                logger.error(e);
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }

    @Override
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

            Contact createdContact = contactService.saveContact(contact);

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
            address.ifPresent(company::setAddress);

            Optional<String> responsible = Optional.ofNullable(request.getParameter("companyresp"));
            if (responsible.isPresent()) {
                User mainContact = userService.findUserById(Integer.parseInt(responsible.get()));
                company.setUser(mainContact);
            }


            Optional<String> text = Optional.ofNullable(request.getParameter("companycomment"));
            if (text.isPresent()) {
                Comment comment = new Comment();
                comment.setText(text.get());
                comment.setDateCreated(new Timestamp(new Date().getTime()));
                comment.setSubject(company);
                User user = new User();
                user.setId(1);
                comment.setUser(user);
                logger.info(String.format("Trying to create comment: %s", comment.getText()));
                Comment createdComment = commentService.saveComment(comment);
                logger.info(String.format("Contact id = %d created", createdComment.getId()));
            }

            Company createdCompany = companyService.saveCompany(company);


            logger.info("NEW COMPANY CREATED:");
            logger.info(createdCompany.getId());

            Optional<String[]> companyContacts = Optional.ofNullable(request.getParameterValues("contactlist[]"));
            if (companyContacts.isPresent()) {
                for (String contactId : companyContacts.get()) {
                    Contact contact = contactService.findContactById(Integer.parseInt(contactId));
                    contact.setCompany(createdCompany);
                    contactService.saveContact(contact);
                }
            }

            Optional<String[]> companyDeals = Optional.ofNullable(request.getParameterValues("addedDeals[]"));
            if (companyDeals.isPresent()) {
                for (String a : companyDeals.get()) {
                    String[] dealValues = a.split(";");
                    Deal deal = new Deal();
                    deal.setName(dealValues[0]);
                    DealStatus status = dealService.findDealStatus(Integer.parseInt(dealValues[1]));
                    deal.setStatus(status);
                    deal.setBudget(Integer.parseInt(dealValues[2]));
                    deal.setDealCompany(createdCompany);
                    deal.setDateCreated(new Timestamp(new Date().getTime()));
                    Optional<Currency> dealCurrency = Optional.ofNullable(currencyService.findCurrencyById(1)); //TODO
                    dealCurrency.ifPresent(deal::setCurrency);
                    dealService.saveDeal(deal);
                }
            }

            Optional<String> companyTags = Optional.ofNullable(request.getParameter("companytags"));
            if (companyTags.isPresent()) {
                String[] tagArray = companyTags.get().split(" ");
                for (String tag : tagArray) {
                    Tag tagInstance = new Tag();
                    tagInstance.setName(tag);
                    tagService.addTagToSubject(createdCompany, tagInstance);
                }
            }

            Optional<String> addTask = Optional.ofNullable(request.getParameter("addTask"));
            if (addTask.isPresent() && "true".equals(addTask.get())) {
                Task task = getTaskFromRequest(request);
                task.setSubject(createdCompany);
                taskService.saveTask(task);
            }
            logger.info("DONE");

        } catch (DataBaseException e) {
            logger.error("Error while creating new company");
            logger.catching(e);
        } catch (ServiceException e) {
            logger.error(e);
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

    private Task getTaskFromRequest(HttpServletRequest request) throws DataBaseException, ServiceException{
        Task task = new Task();
        Optional<String> taskUser = Optional.ofNullable(request.getParameter("taskuser"));
        if (taskUser.isPresent()) {
            User user = userService.findUserById(Integer.parseInt(taskUser.get()));
            task.setUser(user);
        }
        Optional<String> taskType = Optional.ofNullable(request.getParameter("tasktype"));
        taskType.ifPresent(s -> task.setType(TaskType.valueOf(s)));
        Optional<String> taskComment = Optional.ofNullable(request.getParameter("taskcomment"));
        taskComment.ifPresent(task::setComment);
        task.setDateCreated(new Date());
        Optional<String> taskDueDate = Optional.ofNullable(request.getParameter("taskduedate"));
        if (taskDueDate.isPresent() & !"undefined".equals(taskDueDate.get())) {
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
