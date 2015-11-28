package com.becomejavasenior;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@WebServlet("/dealcontroller")
public class DealController extends HttpServlet {
    DaoFactory dao;
    Connection connection;
    Deal createdDeal;
    Comment createdComment;

    public static void main(String[] args) throws DataBaseException {

        DaoFactory dao = null;
        Connection connection = null;
        Deal createdDeal;
        Comment createdComment;

        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        GenericDao<Subject> subjectDao = dao.getDao(connection, Subject.class);
        Subject subject = subjectDao.read(101);
        GenericDao<Comment> commentDao = dao.getDao(connection, Comment.class);
        GenericDao<User> userDao = dao.getDao(connection, User.class);
        Comment comment = new Comment();
        comment.setText("dealcomment");
        comment.setDateCreated(new Timestamp(new Date().getTime()));
        comment.setSubject(subject);
        User user = userDao.read(1);
        comment.setUser(user);
        createdComment = commentDao.create(comment);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {

            Deal deal = new Deal();
            deal.setName(request.getParameter("dealname"));
            deal.setBudget(Integer.parseInt(request.getParameter("dealbudget")));


            GenericDao<Currency> currencyDao = dao.getDao(connection, Currency.class);
            deal.setCurrency(currencyDao.read(1));

            GenericDao<Company> companyDao = dao.getDao(connection, Company.class);
            deal.setDealCompany(companyDao.read(23));

            GenericDao<DealStatus> dealStatusDao = dao.getDao(connection, DealStatus.class);
            DealStatus status = dealStatusDao.read(Integer.parseInt(request.getParameter("dealphase")));
            deal.setStatus(status);

            GenericDao<Contact> contactDao = dao.getDao(connection, Contact.class);
            Contact mainContact = contactDao.read(Integer.parseInt(request.getParameter("dealresp")));
            deal.setMainContact(mainContact);

            GenericDao<Deal> dealDao = dao.getDao(connection, Deal.class);
            createdDeal = dealDao.create(deal);

            GenericDao<Tag> tagDAO = dao.getDao(connection, Tag.class);
            GenericDao<SubjectTag> subjectTagDAO = dao.getDao(connection, SubjectTag.class);
            GenericDao<Subject> subjectDao = dao.getDao(connection, Subject.class);
            String tags = request.getParameter("dealtags");
            String[] tagArray = tags.split(" ");
            Subject subject = subjectDao.read(createdDeal.getId());

            for (String tag : tagArray) {
                Tag tagInstance = new Tag();
                tagInstance.setName(tag);
                Tag returnedTag = tagDAO.create(tagInstance);
                SubjectTag subjectTag = new SubjectTag();
                subjectTag.setTag(returnedTag);
                subjectTag.setSubject(subject);
                subjectTagDAO.create(subjectTag);
            }

            GenericDao<Comment> commentDao = dao.getDao(connection, Comment.class);
            Comment comment = new Comment();
            comment.setText(request.getParameter("dealcomment"));
            comment.setDateCreated(new Timestamp(new Date().getTime()));
            comment.setSubject(subject);
            User user = new User();
            user.setId(1);
            comment.setUser(user);
            createdComment = commentDao.create(comment);


        } catch (DataBaseException e) {
            e.printStackTrace();
        }


        response.getWriter().println("Deal created successfully");
        response.getWriter().println("Deal id = " + createdDeal.getId());
        response.getWriter().println("Comment id = " + createdComment.getId());
//        response.getWriter().println(dao==null);
//        response.getWriter().println(connection==null);
//        response.getWriter().println(request.getParameter("dealname"));
//        response.getWriter().println(request.getParameter("dealphase"));
//        response.getWriter().println(request.getParameter("dealresp"));
//        response.getWriter().println(request.getParameter("dealbudget"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        String nextJSP = "/adddeal.jsp";

        try {
            GenericDao<Contact> contactDao = dao.getDao(connection, Contact.class);
            List<Contact> contactList = contactDao.readAll();
            GenericDao<DealStatus> dealStatusDao = dao.getDao(connection, DealStatus.class);
            List<DealStatus> dealStatusList = dealStatusDao.readAll();
            request.setAttribute("contacts", contactList);
            request.setAttribute("phases", dealStatusList);

        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

   }
}