package com.becomejavasenior;

import com.becomejavasenior.impl.CompanyServiceImpl;
import com.becomejavasenior.impl.ContactServiceImpl;
import com.becomejavasenior.impl.DealServiceImpl;
import com.becomejavasenior.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet("/new_contact_add")
public class NewContactServlet extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(NewContactServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ContactService contactService = new ContactServiceImpl();
        Contact newContact = new Contact();
        try {
            newContact.setName(request.getParameter("name"));
            newContact.setTags(getTagsFromRequest(request.getParameter("tags")));
            newContact.setUser(getUserFromRequest(request.getParameter("responsible")));
            newContact.setPost(request.getParameter("position"));
            if (request.getParameter("phonetype") != null) {
                newContact.setPhoneType(PhoneType.valueOf(request.getParameter("phonetype")));
            }
            newContact.setPhone(request.getParameter("phonenumber"));
            newContact.setEmail(request.getParameter("email"));
            newContact.setSkype(request.getParameter("skype"));

            newContact.setComments(getCommentListFromRequest(request));
            newContact.setFiles(getFileListFromRequest(request)); //not realized
            if(!"".equals(request.getParameter("newdealname"))){
                newContact.setDeals(getDealListFromRequest(request));
            }
            if(!"".equals(request.getParameter("tasktext"))){
                newContact.setTasks(getTaskListFromRequest(request));
            }
            newContact.setCompany(getCompanyFromRequest(request.getParameter("companyid")));
            contactService.saveContact(newContact);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        getServletContext().getRequestDispatcher("/contactlist").forward(request, response);
    }

    private User getUserFromRequest(String id) throws DataBaseException {
        User result = null;
        if (id != null) {
            int key = Integer.parseInt(id);
            UserService userService = new UserServiceImpl();
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
                result.add(tag);
            }
        }
        return result;
    }

    private Company getCompanyFromRequest(String id) throws DataBaseException {
        Company result = null;
        if (id != null) {
            int key = Integer.parseInt(id);
            CompanyService companyService = new CompanyServiceImpl();
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

    private List<Deal> getDealListFromRequest(HttpServletRequest request) {
        List<Deal> result = new ArrayList<>();
        Deal deal = new Deal();
        deal.setName(request.getParameter("newdealname"));
        String dealType = request.getParameter("dealtype");
        if(dealType!=null&&!"".equals(dealType)){
            LOGGER.info(dealType);
            int dealStatusId = Integer.parseInt(dealType);
            try {
                DealStatus dealStatus = new DealServiceImpl().findDealStatus(dealStatusId);
                deal.setStatus(dealStatus);
            } catch (DataBaseException e) {
                LOGGER.error(e);
            }
        }
        String budget = request.getParameter("budget");
        if(budget!=null&&!"".equals(budget)){
            deal.setBudget(Integer.parseInt(budget));
        }
        result.add(deal);
        return result;
    }

    private List<Task> getTaskListFromRequest(HttpServletRequest request) throws DataBaseException {
        List<Task> result = new ArrayList<>();
        Task task = new Task();
        task.setUser(getUserFromRequest(request.getParameter("taskresponsible")));
        String taskType = request.getParameter("tasktype");
        if (taskType != null && !"".equals(taskType)) {
            task.setType(TaskType.valueOf(taskType));
        }
        task.setComment(request.getParameter("tasktext"));
        task.setDateCreated(new Date());
        if (!"".equals(request.getParameter("duedate"))) {
            String duedate = request.getParameter("duedate");
            String duetime = request.getParameter("duetime");
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
        result.add(task);
        return result;
    }
}
