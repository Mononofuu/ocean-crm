package com.becomejavasenior;

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
    private DaoFactory dao;

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
            dao = new PostgreSqlDaoFactory();
            GenericDao contactDao = dao.getDao(Contact.class);
            List<Contact> allContacts = contactDao.readAll();
            Collections.sort(allContacts, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            req.setAttribute("contactlist", allContacts);
            GenericDao companyDao = dao.getDao(Company.class);
            List<Company> allCompanies = companyDao.readAll();
            Collections.sort(allCompanies, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
            req.setAttribute("companylist", allCompanies);
            GenericDao tagsDao = dao.getDao(Tag.class);
            List<Tag> tags = tagsDao.readAll();
            Collections.sort(tags, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
            req.setAttribute("tags", tags);
            GenericDao<TaskType> usersDao = dao.getDao(User.class);
            req.setAttribute("users", usersDao.readAll());
            getServletContext().getRequestDispatcher("/jsp/viewallcontacts.jsp").forward(req,resp);
        } catch (DataBaseException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        } catch (ServletException e) {
            logger.error("Error when prepearing data for tasklist.jsp",e);
        }
    }
}
