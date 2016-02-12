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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name = "contactlist", urlPatterns = "/contactlist")
public class ViewAllContactsServlet extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(TaskListServlet.class);
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            List<Contact> allContacts;
            List<Company> allCompanies;
            if (req.getParameter("filtername") != null) {
                allContacts = contactService.getAllContactsByParameters(req.getParameterMap());
                allCompanies = companyService.getAllCompanyesByParameters(req.getParameterMap());
            } else {
                allContacts = contactService.findContacts();
                allCompanies = companyService.findCompanies();
            }
            if(allContacts!=null){
                Collections.sort(allContacts, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            }
            if(allCompanies!=null){
                Collections.sort(allCompanies, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            }
            req.setAttribute("contactlist", allContacts);
            req.setAttribute("companylist", allCompanies);
            List<Tag> tags = new ArrayList<>();
            tags.addAll(contactService.getAllContactTags());
            tags.addAll(companyService.getAllCompanyTags());
            Collections.sort(tags, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
            req.setAttribute("tags", tags);
            req.setAttribute("users", userService.getAllUsers());
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        getServletContext().getRequestDispatcher("/jsp/viewallcontacts.jsp").forward(req, resp);
    }
}
