package com.becomejavasenior;

import com.becomejavasenior.impl.CompanyServiceImpl;
import com.becomejavasenior.impl.ContactServiceImpl;
import com.becomejavasenior.impl.TagServiceImpl;
import com.becomejavasenior.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name = "contactlist", urlPatterns = "/contactlist")
public class ViewAllContactsServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(TaskListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        try {
            ContactService contactService = new ContactServiceImpl();
            CompanyService companyService = new CompanyServiceImpl();
            UserService userService = new UserServiceImpl();
            TagService tagService = new TagServiceImpl();
            List<Contact> allContacts = contactService.findContacts();
            Collections.sort(allContacts, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            req.setAttribute("contactlist", allContacts);
            List<Company> allCompanies = companyService.findCompanies();
            Collections.sort(allCompanies, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
            req.setAttribute("companylist", allCompanies);
            List<Tag> tags = tagService.getAllTags();
            Collections.sort(tags, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
            req.setAttribute("tags", tags);
            req.setAttribute("users", userService.getAllUsers());
            getServletContext().getRequestDispatcher("/jsp/viewallcontacts.jsp").forward(req,resp);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        }
    }
}
