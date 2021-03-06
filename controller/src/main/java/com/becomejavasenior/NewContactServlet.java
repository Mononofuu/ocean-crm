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
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet("/new_contact_add")
public class NewContactServlet extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(NewContactServlet.class);
    @Autowired
    private DealService dealService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Contact newContact = new Contact();
        try {
            newContact.setName(request.getParameter("name"));
            newContact.setTags(getTagsFromRequest(request.getParameter("tags")));
            newContact.setUser(getUserFromRequest(request.getParameter("responsible")));
            newContact.setPost(request.getParameter("position"));
            if (!"".equals(request.getParameter("phonetype"))) {
                newContact.setPhoneType(PhoneType.valueOf(request.getParameter("phonetype")));
            }
            newContact.setPhone(request.getParameter("phonenumber"));
            newContact.setEmail(request.getParameter("email"));
            newContact.setSkype(request.getParameter("skype"));

            newContact.setComments(getCommentListFromRequest(request));
            newContact.setFiles(getFileListFromRequest(request)); //not realized
            newContact.setCompany(getCompanyFromRequest(request.getParameter("companyid")));
            newContact = contactService.saveContact(newContact);
            if(!"".equals(request.getParameter("newdealname"))){
                saveDealFromRequest(request, newContact);
            }
            if(!"".equals(request.getParameter("tasktext"))){
                saveTaskFromRequest(request, newContact);
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        getServletContext().getRequestDispatcher("/contactlist").forward(request, response);
    }

    private User getUserFromRequest(String id) throws DataBaseException, ServiceException {
        User result = null;
        if (id != null) {
            int key = Integer.parseInt(id);
            result = userService.findUserById(key);
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

    private Company getCompanyFromRequest(String id) throws DataBaseException {
        Company result = null;
        if (id != null) {
            int key = Integer.parseInt(id);
            result = companyService.findCompanyById(key);
        }
        return result;
    }

    private List<Comment> getCommentListFromRequest(HttpServletRequest request) {
        List<Comment> result = new ArrayList<>();
        Comment comment = new Comment();
        comment.setDateCreated(new Date());
        comment.setUser((User) request.getSession().getAttribute("user"));
        comment.setText(request.getParameter("notes"));
        result.add(comment);
        return result;
    }

    private List<File> getFileListFromRequest(HttpServletRequest request) {
        List<File> result = new ArrayList<>();
        return result;
    }

    private void saveDealFromRequest(HttpServletRequest request, Contact contact) throws DataBaseException{
        Deal deal = new Deal();
        deal.setMainContact(contact);
        deal.setName(request.getParameter("newdealname"));
        String dealType = request.getParameter("dealtype");
        if(dealType!=null&&!"".equals(dealType)){
            LOGGER.info(dealType);
            int dealStatusId = Integer.parseInt(dealType);
            try {
                DealStatus dealStatus = dealService.findDealStatus(dealStatusId);
                deal.setStatus(dealStatus);
            } catch (DataBaseException e) {
                LOGGER.error(e);
            }
        }
        String budget = request.getParameter("budget");
        if(budget!=null&&!"".equals(budget)){
            deal.setBudget(Integer.parseInt(budget));
        }
        dealService.saveDeal(deal);
    }

    private void saveTaskFromRequest(HttpServletRequest request, Contact contact) throws DataBaseException, ServiceException {
        Task task = new Task();
        task.setSubject(contact);
        task.setUser(getUserFromRequest(request.getParameter("taskresponsible")));
        String taskType = request.getParameter("tasktype");
        if (taskType != null && !"".equals(taskType)) {
            task.setType(TaskType.valueOf(taskType));
        }else{
            task.setType(TaskType.OTHER);
        }
        task.setComment(request.getParameter("tasktext"));
        task.setDateCreated(new Date());
        if (!"".equals(request.getParameter("duedate"))) {
            String duedate = request.getParameter("duedate");
            String duetime = request.getParameter("duetime");
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
            String period = request.getParameter("period");
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
        taskService.saveTask(task);
    }
}
