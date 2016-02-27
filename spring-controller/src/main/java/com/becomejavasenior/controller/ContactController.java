package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import com.becomejavasenior.validation.CompanyValidator;
import com.becomejavasenior.validation.ContactFormHandler;
import com.becomejavasenior.validation.ContactFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping(value = "/contacts")
public class ContactController {
    private final static Logger LOGGER = LogManager.getLogger(ContactController.class);
    private final static int DEAL_PRIMARY_CONTACT = 1;
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private DealService dealService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ContactFormValidator contactFormValidator;
    @Autowired
    private CompanyValidator companyValidator;

    @InitBinder("contactForm")
    protected void initBinder(WebDataBinder binder){
        binder.setValidator(contactFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String allContactsList(@RequestParam Map<String, String> filterParams, Model model){
        try {
            List<Contact> allContacts = contactService.findContacts();
            List<Company> allCompanies = companyService.findCompanies();
            if(allContacts!=null){
                Collections.sort(allContacts, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            }
            if(allCompanies!=null){
                Collections.sort(allCompanies, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            }
            model.addAttribute("contactlist", allContacts);
            model.addAttribute("companylist", allCompanies);
            fillModelForContactList(model);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return "viewallcontacts";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String allContactsByFilter(@RequestParam Map<String, String> filterParams, Model model){
        try {
            List<Contact> allContacts = contactService.getAllContactsByParameters(convertParamMap(filterParams));
            List<Company> allCompanies = companyService.getAllCompanyesByParameters(convertParamMap(filterParams));
            if(allContacts!=null){
                Collections.sort(allContacts, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            }
            if(allCompanies!=null){
                Collections.sort(allCompanies, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            }
            model.addAttribute("contactlist", allContacts);
            model.addAttribute("companylist", allCompanies);
            fillModelForContactList(model);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return "viewallcontacts";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String newContact(Model model) throws DataBaseException, ServiceException{
        model.addAttribute("contactForm", new ContactFormHandler());
        fillModelForNewContact(model);
        return "newcontact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveOrUpdateContact(@ModelAttribute("contactForm") @Validated ContactFormHandler contactForm,
                                      BindingResult contactResult, Model model,
                                      @RequestParam Map<String, String> parameters) throws DataBaseException, ServiceException{
        fillModelForNewContact(model);
        if (contactResult.hasErrors()) {
            LOGGER.debug(contactResult.toString());
            return "newcontact";
        } else {
            Contact contact = contactForm.getContact();
            if(parameters.get("company")!=null&&!"".equals(parameters.get("company"))){
                contact.setCompany(companyService.findCompanyById(Integer.parseInt(parameters.get("company"))));
            }
            contact.setTags(getTagsFromRequest(contactForm.getTags()));
            contact.setUser(userService.findUserById(contactForm.getUserContactId()));
            if(contactForm.getContactComment()!=null){
                setCommentList(contact, contactForm.getContactComment());
            }

            contact = contactService.saveContact(contact);
            contactForm.setContact(contact);
            if(contactForm.getTask().getComment()!=null){
                saveTaskFromRequest(contactForm);
            }
            if(contactForm.getDeal().getName()!=null){
                saveDealFromRequest(contactForm);
            }
            model.addAttribute("resultmessage", "label.contactadded");
            return "newcontact";
        }
    }

    private void saveDealFromRequest(ContactFormHandler contactForm)throws DataBaseException{
        Deal deal = contactForm.getDeal();
        deal.setMainContact(contactForm.getContact());
        if(deal.getStatus()==null){
            deal.setStatus(dealService.findDealStatus(DEAL_PRIMARY_CONTACT));
        }
        dealService.saveDeal(contactForm.getDeal());
    }

    private void fillModelForContactList(Model model) throws DataBaseException, ServiceException{
        List<Tag> tags = new ArrayList<>();
        tags.addAll(contactService.getAllContactTags());
        tags.addAll(companyService.getAllCompanyTags());
        Collections.sort(tags, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
        model.addAttribute("tags", tags);
        model.addAttribute("users", userService.getAllUsers());
    }

    private void fillModelForNewContact(Model model) throws DataBaseException, ServiceException{
        model.addAttribute("userslist", userService.getAllUsers());
        model.addAttribute("companylist", companyService.findCompanies());
        model.addAttribute("phonetypelist", PhoneType.values());
        model.addAttribute("tasktypes", TaskType.values());
        model.addAttribute("dealstatuses", dealService.getAllDealStatuses());
    }

    private Map<String, String[]> convertParamMap(Map<String, String> filterParams){
        Map<String, String[]> result = new HashMap<>();
        for(Map.Entry<String, String> entry:filterParams.entrySet()){
            result.put(entry.getKey(), new String[]{entry.getValue()});
        }
        return result;
    }

    private Set<Tag> getTagsFromRequest(String str) {
        Set<Tag> result = new HashSet<>();
        if (str != null) {
            String[] temp = str.split(" ");
            for (String stringTag : temp) {
                Tag tag = new Tag();
                tag.setName(stringTag);
                tag.setSubjectType(SubjectType.CONTACT_TAG);
                result.add(tag);
            }
        }
        return result;
    }

    private void setCommentList(Contact contact, String commentString)throws ServiceException{
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setText(commentString);
        comment.setDateCreated(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        comment.setUser(userService.getUserByLogin(auth.getName()));
        comments.add(comment);
        contact.setComments(comments);
    }

    private void saveTaskFromRequest(ContactFormHandler contactForm) throws DataBaseException, ServiceException {
        Task task = contactForm.getTask();
        task.setSubject(contactForm.getContact());
        task.setDateCreated(new Date());
        if (!"".equals(contactForm.getDueDate())) {
            String duedate = contactForm.getDueDate();
            String duetime = contactForm.getDueTime();
            if("".equals(duetime)){
                duetime = "23:59";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
            try {
                task.setDueTime(dateFormat.parse(duedate + duetime));
            } catch (ParseException e) {
                LOGGER.error(e);
            }
        } else {
            String period = contactForm.getPeriod();
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
        }
        taskService.saveTask(task);
    }
}
