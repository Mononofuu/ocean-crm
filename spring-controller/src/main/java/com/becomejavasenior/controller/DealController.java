package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import com.becomejavasenior.validation.DealFormHandler;
import com.becomejavasenior.validation.DealFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Controller
@RequestMapping(value = "/deals")
public class DealController {
    private static final Logger LOGGER = LogManager.getLogger(DealController.class);

    @Autowired
    private DealService dealService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DealFormValidator dealFormValidator;

    @Autowired
    private TaskService taskService;

    @InitBinder("contactForm")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(dealFormValidator);
    }

    @RequestMapping()
    public String getAllDealsForList(Model model) throws DataBaseException, ServiceException {
        List<Deal> dealsList = dealService.findDeals();
        List<DealStatus> dealStatusList = dealService.getAllDealStatuses();
        List<User> userList = userService.getAllUsersLite();
        List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
        List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));

        model.addAttribute("deals", dealsList);
        model.addAttribute("deals_map", dealStatusList);
        model.addAttribute("managers", userList);
        model.addAttribute("filterperiod", filterPeriods);
        model.addAttribute("filtertask", filterTaskTypes);

        return "dealslist";
    }

    @RequestMapping("/pyramid")
    public String getAllDealsForPyramid(Model model) throws DataBaseException {
        List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
        List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));
        List<Contact> contacts = contactService.findContactsLite();
        List<Filter> filters = dealService.findDealFilters();
        model.addAttribute("filterperiod", filterPeriods);
        model.addAttribute("filtertask", filterTaskTypes);
        model.addAttribute("managers", contacts);
        model.addAttribute("filters", filters);

        return "dealspyramid";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String newDeal(Model model) throws DataBaseException, ServiceException {
        model.addAttribute("dealForm", new DealFormHandler());
        fillModelForNewDeal(model);
        return "newdeal";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveOrUpdateDeal(@ModelAttribute("dealForm") DealFormHandler dealForm,
                                   Model model, BindingResult result) throws DataBaseException, ServiceException {
        fillModelForNewDeal(model);
        dealFormValidator.validate(dealForm, result);
        if (result.hasErrors()) {
            LOGGER.debug(result.toString());
            return "newdeal";
        } else {
            Deal deal = dealForm.getDeal();
            deal.setDateCreated(new Timestamp(new Date().getTime()));
            deal.setTags(getTagsFromRequest(dealForm.getTags()));
            deal.setResponsible(userService.findUserById(dealForm.getUserContactId()));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            deal.setUser(userService.getUserByLogin(auth.getName()));
            if (dealForm.getDealComment() != null) {
                setCommentList(deal, dealForm.getDealComment());
            }
            if (dealForm.getDealCompanyId() != 0) {
                deal.setDealCompany(companyService.findCompanyById(dealForm.getDealCompanyId()));
            }
            Task task = null;
            if (dealForm.getTaskPeriod() != null | dealForm.getDueDate() != null) {
                task = dealForm.getTask();
                if (dealForm.getTaskPeriod() != null) {
                    String period = dealForm.getTaskPeriod();
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
                        case "nextyear":
                            c.set(Calendar.DAY_OF_MONTH, 0);
                            c.set(Calendar.MONTH, 0);
                            c.add(Calendar.YEAR, 2);
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
                    }
                    task.setDueTime(c.getTime());
                } else {
                    String duedate = dealForm.getDueDate();
                    String duetime = dealForm.getDueTime();
                    if ("".equals(duetime)) {
                        duetime = "23:59";
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
                    try {
                        task.setDueTime(dateFormat.parse(duedate + duetime));
                    } catch (ParseException e) {
                        LOGGER.error(e);
                    }
                }
                task.setUser(userService.findUserById(dealForm.getTaskUserId()));
                task.setDateCreated(new Timestamp(new Date().getTime()));
                task = taskService.saveTask(task);
                List<Task> taskList = new ArrayList<>();
                taskList.add(task);
                deal.setTasks(taskList);
            }

            deal = dealService.saveDeal(deal);
            if (task != null) {
                task.setSubject(deal);
                taskService.saveTask(task);
            }
            dealForm.setDeal(deal);

            model.addAttribute("resultmessage", "label.dealadded");
            return getAllDealsForList(model);
        }
    }

    @RequestMapping(value = "/add", params = "savecompany", method = RequestMethod.POST)
    public String saveCompany(@ModelAttribute("dealForm") DealFormHandler dealForm,
                              Model model) throws DataBaseException, ServiceException {
        LOGGER.debug(dealForm.getCompany());
        Company company = dealForm.getCompany();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        company.setUser(userService.getUserByLogin(auth.getName()));
        company = companyService.saveCompany(company);
        dealForm.setCompany(company);
        fillModelForNewDeal(model);
        model.addAttribute("dealForm", new DealFormHandler());
        model.addAttribute("resultmessage", "label.dealadded");
        return "newdeal";
    }

    @RequestMapping(value = "/add", params = "savecontact", method = RequestMethod.POST)
    public String saveContact(@ModelAttribute("dealForm") DealFormHandler dealForm,
                              Model model) throws DataBaseException, ServiceException {
        LOGGER.debug(dealForm.getContact());
        Contact contact = dealForm.getContact();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        contact.setUser(userService.getUserByLogin(auth.getName()));
        if (dealForm.getContactCompanyId() != 0) {
            contact.setCompany(companyService.findCompanyById(dealForm.getContactCompanyId()));
        }
        contact = contactService.saveContact(contact);
        dealForm.setContact(contact);
        fillModelForNewDeal(model);
        model.addAttribute("dealForm", new DealFormHandler());
        model.addAttribute("resultmessage", "label.dealadded");
        return "newdeal";
    }


    private void fillModelForNewDeal(Model model) throws DataBaseException, ServiceException {
        model.addAttribute("userslist", userService.getAllUsers());
        model.addAttribute("contactlist", contactService.findContacts());
        model.addAttribute("companylist", companyService.findCompanies());
        model.addAttribute("phonetypelist", PhoneType.values());
        model.addAttribute("tasktypes", TaskType.values());
        model.addAttribute("dealstatuses", dealService.getAllDealStatuses());
    }

    private Set<Tag> getTagsFromRequest(String str) {
        Set<Tag> result = new HashSet<>();
        if (str != null) {
            String[] temp = str.split(" ");
            for (String stringTag : temp) {
                Tag tag = new Tag();
                tag.setName(stringTag);
                tag.setSubjectType(SubjectType.DEAL_TAG);
                result.add(tag);
            }
        }
        return result;
    }

    private void setCommentList(Deal deal, String commentString) throws ServiceException, DataBaseException {
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setText(commentString);
        comment.setDateCreated(new Timestamp(new Date().getTime()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        comment.setUser(userService.getUserByLogin(auth.getName()));
        comment = commentService.saveComment(comment);
        comments.add(comment);
        deal.setComments(comments);
    }

}
