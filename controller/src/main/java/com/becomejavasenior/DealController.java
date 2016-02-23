package com.becomejavasenior;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DealController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DealController.class);
    private static final String NEXT_JSP = "/jsp/newdeal.jsp";
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.catching(e);
        }
        String action = request.getParameter("action");
        LOGGER.debug(action);
        switch (action) {
            case "newdeal":
                newDeal(request);
                break;
            case "newcontact":
                newContact(request);
                break;
            case "newcompany":
                newCompany(request);
                break;
            default:
        }

    }

    private void newContact(HttpServletRequest request) {
        try {
            Contact contact = new Contact();

            Optional<String> contactName = Optional.ofNullable(request.getParameter("contactname"));
            contactName.ifPresent(contact::setName);

            Optional<String> contactCompany = Optional.ofNullable(request.getParameter("contactcompany"));
            if (contactCompany.isPresent()) {
                Company company = companyService.findCompanyById(Integer.parseInt(contactCompany.get()));
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

            Contact createdContact = contactService.saveContact(contact);

            LOGGER.info("NEW CONTACT CREATED:");
            LOGGER.info(createdContact.getId());

        } catch (DataBaseException e) {
            LOGGER.error("Error while creating new contact");
            LOGGER.catching(e);
        }
    }

    private void newCompany(HttpServletRequest request) {
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
                    LOGGER.catching(e);
                }
            });

            Optional<String> companyAddress = Optional.ofNullable(request.getParameter("companyaddress"));
            companyAddress.ifPresent(company::setAddress);

            Company createdCompany = companyService.saveCompany(company);

            LOGGER.info("NEW COMPANY CREATED:");
            LOGGER.info(createdCompany.getId());
        } catch (DataBaseException e) {
            LOGGER.error("Error while creating new company");
            LOGGER.catching(e);
        }
    }

    private void newDeal(HttpServletRequest request) {
        Deal createdDeal;
        try {
            Deal deal = new Deal();

            Optional<String> dealName = Optional.ofNullable(request.getParameter("dealname"));
            dealName.ifPresent(deal::setName);

            Optional<String> dealResp = Optional.ofNullable(request.getParameter("dealresp"));
            if (dealResp.isPresent()) {
                User responsible = userService.findUserById(Integer.parseInt(dealResp.get()));
                deal.setResponsible(responsible);
            }

            Optional<String> dealBudget = Optional.ofNullable(request.getParameter("dealbudget"));
            dealBudget.ifPresent(s -> deal.setBudget(Integer.parseInt(s)));

            Optional<String> dealStatus = Optional.ofNullable(request.getParameter("dealstatus"));
            if (dealStatus.isPresent()) {
                DealStatus status = dealService.findDealStatus(Integer.parseInt(dealStatus.get()));
                deal.setStatus(status);
            }

            Optional<String> dealCompany = Optional.ofNullable(request.getParameter("dealcompany"));
            if (dealCompany.isPresent()) {
                deal.setDealCompany(companyService.findCompanyById(Integer.parseInt(dealCompany.get())));
            }

            Optional<Currency> dealCurrency = Optional.ofNullable(currencyService.findCurrencyById(1)); //TODO
            dealCurrency.ifPresent(deal::setCurrency);

            deal.setDateWhenDealClose(null);

            Optional<String> dealCreatedDate = Optional.ofNullable(request.getParameter("dealcreated"));
            if (dealCreatedDate.isPresent()) {
                LOGGER.debug(dealCreatedDate.get());
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dealCreatedDate.get());
                deal.setDateCreated(new Timestamp(date.getTime()));
            }

            Optional<String[]> contactList = Optional.ofNullable(request.getParameterValues("dealcontactlist[]"));
            if (contactList.isPresent()) {
                int mainContactId = Integer.parseInt(contactList.get()[0]);
                Contact mainContact = contactService.findContactById(mainContactId);
                deal.setMainContact(mainContact);
            }

            deal.setUser((User) request.getSession().getAttribute("user"));

            LOGGER.info("TRYING TO CREATE DEAL");
            createdDeal = dealService.saveDeal(deal);
            LOGGER.info("DEAL CREATED");

            if (contactList.isPresent()) {
                List<Contact> dealContacts = new ArrayList<>();

                for (String contactId : contactList.get()) {
                    Contact contact = contactService.findContactById(Integer.parseInt(contactId));
                    dealService.addContactToDeal(createdDeal, contact);
                    dealContacts.add(contact);
                }
                deal.setContacts(dealContacts);
            }

            Optional<String> dealTags = Optional.ofNullable(request.getParameter("dealtags"));
            if (dealTags.isPresent()) {
                String[] tagArray = dealTags.get().trim().split(" ");

                for (String tag : tagArray) {
                    Tag tagInstance = new Tag();
                    tagInstance.setName(tag);
                    tagService.addTagToSubject(createdDeal, tagInstance);
                }
            }

            Optional<String> dealComment = Optional.ofNullable(request.getParameter("dealcomment"));
            if (dealComment.isPresent()) {
                Comment comment = new Comment();
                comment.setText(dealComment.get());
                comment.setDateCreated(new Timestamp(new Date().getTime()));
                comment.setSubject(createdDeal);
                User user = new User();
                user.setId(1);
                comment.setUser(user);
                LOGGER.info(String.format("Trying to create comment: %s", comment.getText()));
                Comment createdComment = commentService.saveComment(comment);
                LOGGER.info(String.format("Contact id = %d created", createdComment.getId()));
            }

            Optional<String> addTask = Optional.ofNullable(request.getParameter("addTask"));
            if (addTask.isPresent() && "true".equals(addTask.get())) {
                LOGGER.debug("Adding task");
                Task task = getTaskFromRequest(request, createdDeal);
                taskService.saveTask(task);
            }

            LOGGER.info("Deal created successfully");
            LOGGER.info(String.format("Deal id = %d", createdDeal.getId()));

        } catch (DataBaseException e) {
            LOGGER.error("Error while creating new deal");
            LOGGER.catching(e);
        } catch (ParseException e) {
            LOGGER.catching(e);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        LOGGER.info("GET action = " + action);

        if (action == null || action.isEmpty()) {
            redirectTo(request, response, NEXT_JSP);
        } else {
            String json = null;
            try {
                switch (action) {
                    case "getCompanies":
                        List<Company> companyList = companyService.findCompaniesLite();
                        json = new Gson().toJson(companyList);
                        break;
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
                        redirectTo(request, response, NEXT_JSP);
                }
            } catch (DataBaseException e) {
                LOGGER.error("Error while getting json data");
                LOGGER.catching(e);
            } catch (ServiceException e) {
                LOGGER.error(e);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                if (json != null) {
                    response.getWriter().write(json);
                }
            } catch (IOException e) {
                LOGGER.catching(e);
            }
        }
    }

    private void redirectTo(HttpServletRequest request, HttpServletResponse response, String page) {
        try {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            LOGGER.info(String.format("REDIRECTING TO %s", NEXT_JSP));
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.catching(e);
        }
    }

    private Task getTaskFromRequest(HttpServletRequest request, Deal createdDeal) throws DataBaseException, ServiceException {
        Task task = new Task();
        Optional<String> taskUser = Optional.ofNullable(request.getParameter("taskuser"));
        if (taskUser.isPresent()) {
            User user = userService.findUserById(Integer.parseInt(taskUser.get()));
            task.setUser(user);
        }
        task.setSubject(createdDeal);
        Optional<String> taskType = Optional.ofNullable(request.getParameter("tasktype"));
        taskType.ifPresent(s -> task.setType(TaskType.valueOf(s)));
        Optional<String> taskComment = Optional.ofNullable(request.getParameter("taskcomment"));
        taskComment.ifPresent(task::setComment);
        task.setDateCreated(new Date());
        Optional<String> taskDueDate = Optional.ofNullable(request.getParameter("taskduedate"));
        if (taskDueDate.isPresent() && !"undefined".equals(taskDueDate.get())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            try {
                task.setDueTime(dateFormat.parse(taskDueDate.get()));
            } catch (ParseException e) {
                LOGGER.catching(e);
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
                default:
                    break;
            }
            task.setDueTime(c.getTime());
        }
        return task;
    }
}
