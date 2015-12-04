package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DealController extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealController.class);
    private final static String nextJSP = "/jsp/newdeal.jsp";
    private DaoFactory dao;
    private Deal createdDeal;
    private Comment createdComment;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {

            Deal deal = new Deal();
            deal.setName(request.getParameter("dealname"));
            deal.setBudget(Integer.parseInt(request.getParameter("dealbudget")));

            GenericDao<Currency> currencyDao = dao.getDao(Currency.class);
            deal.setCurrency(currencyDao.read(1));//TODO

            GenericDao<Company> companyDao = dao.getDao(Company.class);
            deal.setDealCompany(companyDao.read(Integer.parseInt(request.getParameter("dealcompany"))));

            GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
            DealStatus status = dealStatusDao.read(Integer.parseInt(request.getParameter("dealphase")));
            deal.setStatus(status);

            GenericDao<Contact> contactDao = dao.getDao(Contact.class);
            Contact mainContact = contactDao.read(Integer.parseInt(request.getParameter("dealresp")));
            deal.setMainContact(mainContact);

            GenericDao<Deal> dealDao = dao.getDao(Deal.class);
            logger.info("TRYING TO CREATE DEAL");
            createdDeal = dealDao.create(deal);

            GenericDao<Tag> tagDAO = dao.getDao(Tag.class);
            GenericDao<SubjectTag> subjectTagDAO = dao.getDao(SubjectTag.class);
            GenericDao<Subject> subjectDao = dao.getDao(Subject.class);
            String tags = request.getParameter("dealtags");
            String[] tagArray = tags.split(" ");
            Subject subject = subjectDao.read(createdDeal.getId());

            for (String tag : tagArray) {
                Tag tagInstance = new Tag();
                tagInstance.setName(tag);
                Tag returnedTag = tagDAO.create(tagInstance);
                logger.info(String.format("Trying to create tag: %s", tag));
                SubjectTag subjectTag = new SubjectTag();
                subjectTag.setTag(returnedTag);
                subjectTag.setSubject(subject);
                subjectTagDAO.create(subjectTag);
            }

            GenericDao<Comment> commentDao = dao.getDao(Comment.class);
            Comment comment = new Comment();
            comment.setText(request.getParameter("dealcomment"));
            comment.setDateCreated(new Timestamp(new Date().getTime()));
            comment.setSubject(subject);
            User user = new User();
            user.setId(1);
            comment.setUser(user);
            logger.info(String.format("Trying to create comment: %s", comment.getText()));
            createdComment = commentDao.create(comment);


        } catch (DataBaseException e) {
            logger.error("Error while creating entities");
            logger.catching(e);
        }


        response.getWriter().println("DEAL CREATED SUCCESSFULLY");
        logger.info("Deal created successfully");
        logger.info(String.format("Deal id = %d", createdDeal.getId()));
        logger.info(String.format("Deal name: %s", request.getParameter("dealname")));
        logger.info(String.format("Deal phase: %s", request.getParameter("dealphase")));
        logger.info(String.format("Deal budget: %s", request.getParameter("dealbudget")));
        logger.info(String.format("Deal responsible: %s", request.getParameter("dealresp")));
        logger.info(String.format("Deal company: %s", request.getParameter("dealcompany")));
        logger.info(String.format("Comment id = %d", createdComment.getId()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        logger.info("Collecting contacts, phases and companies data");

        try {
            GenericDao<Contact> contactDao = dao.getDao(Contact.class);
            List<Contact> contactList = contactDao.readAll();
            GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
            List<DealStatus> dealStatusList = dealStatusDao.readAll();
            GenericDao<Company> companyDao = dao.getDao(Company.class);
            List<Company> companyList = companyDao.readAll();
            request.setAttribute("contacts", contactList);
            request.setAttribute("phases", dealStatusList);
            request.setAttribute("companies", companyList);

        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
        logger.info(String.format("REDIRECTING TO %s", nextJSP));

    }
}
