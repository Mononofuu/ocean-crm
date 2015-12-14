package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private Logger logger = LogManager.getLogger(NewContactServlet.class);
    private DaoFactory dao;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            dao = new PostgreSqlDaoFactory();
            Contact newContact = new Contact();
            newContact.setName(request.getParameter("name"));
            newContact.setTags(getTagsFromRequest(request.getParameter("tags")));
            newContact.setUser(getUserFromRequest(request.getParameter("responsible")));
            newContact.setPost(request.getParameter("position"));
            if(request.getParameter("phonetype")!=null){
                newContact.setPhoneType(PhoneType.valueOf(request.getParameter("phonetype")));
            }
            newContact.setPhone(request.getParameter("phonenumber"));
            newContact.setEmail(request.getParameter("email"));
            newContact.setSkype(request.getParameter("skype"));

            newContact.setComments(getCommentListFromRequest(request));
            newContact.setFiles(getFileListFromRequest(request)); //not realized
            newContact.setDeals(getDealListFromRequest(request));
            newContact.setTasks(getTaskListFromRequest(request));
            newContact.setCompany(getCompanyFromRequest(request.getParameter("companyid")));
            GenericDao<Contact>  contactDao = dao.getDao(Contact.class);
            Contact returnedContact = contactDao.create(newContact);
        } catch (DataBaseException e) {
            logger.error("Error while adding new contact", e);
        }


    }

    private User getUserFromRequest(String id)throws DataBaseException{
        User result=null;
        if(id!=null){
            int key = Integer.parseInt(id);
            GenericDao<User> userDao = dao.getDao(User.class);
            result =  userDao.read(key);
        }
        return result;
    }

    private Set<Tag> getTagsFromRequest(String str){
        Set<Tag> result = new HashSet<>();
        if(str!=null){
            String[] temp = str.split(" ");
            for(String stringTag:temp){
                Tag tag = new Tag();
                tag.setName(stringTag);
                result.add(tag);
            }
        }
        return result;
    }

    private Company getCompanyFromRequest(String id)throws DataBaseException{
        Company result=null;
        if(id!=null){
            int key = Integer.parseInt(id);
            GenericDao<Company> companyDao = dao.getDao(Company.class);
            result =  companyDao.read(key);
        }
        return result;
    }

    private List<Comment> getCommentListFromRequest(HttpServletRequest request){
        List<Comment> result= new ArrayList<>();
        Comment comment = new Comment();
        comment.setDateCreated(new Date());
        //comment.setUser();  должен устанавливаться User из текущей сессии
        comment.setText(request.getParameter("notes"));
        result.add(comment);
        return result;
    }

    private List<File> getFileListFromRequest(HttpServletRequest request){
        List<File> result = new ArrayList<>();
        return result;
    }

    private List<Deal> getDealListFromRequest(HttpServletRequest request){
        List<Deal> result = new ArrayList<>();
        Deal deal = new Deal();
        deal.setName(request.getParameter("newdealname"));
        // deal.setStatus();  Enum?
        try{
            deal.setBudget(Integer.parseInt(request.getParameter("budget")));
        }catch (NumberFormatException e){
            /*NOP*/
        }
        result.add(deal);
        return result;
    }

    private List<Task> getTaskListFromRequest(HttpServletRequest request)throws DataBaseException{
        List<Task> result = new ArrayList<>();
        Task task = new Task();
        task.setUser(getUserFromRequest(request.getParameter("taskresponsible")));
        if(request.getParameter("tasktype")!=null&&!"".equals(request.getParameter("tasktype"))){
            task.setType(TaskType.valueOf(request.getParameter("tasktype")));
        }
        task.setComment(request.getParameter("tasktext"));
        task.setDateCreated(new Date());
        if(!"".equals(request.getParameter("duedate"))){
            String duedate = request.getParameter("duedate");
            String duetime = request.getParameter("duetime");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyyHH:mm");
            try {
                task.setDueTime(dateFormat.parse(duedate+duetime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            String period = request.getParameter("period");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            switch (period){
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
                    c.add(Calendar.DAY_OF_MONTH,14);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "nextmonth":
                    c.set(Calendar.DAY_OF_MONTH,0);
                    c.add(Calendar.MONTH, 2);
                    c.add(Calendar.MINUTE, -1);
                    break;
                case "nextyear":
                    c.set(Calendar.DAY_OF_MONTH,0);
                    c.set(Calendar.MONTH,0);
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
