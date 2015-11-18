package com.becomejavasenior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by antonsakhno on 04.11.15.
 */
@WebServlet("/")
public class NewContactServlet extends HttpServlet {
    DaoFactory dao;
    Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        this.process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new PostgreSqlDaoFactory();
            connection = dao.getConnection();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Contact newContact = new Contact();
        newContact.setName(request.getParameter("name"));
        newContact.setTags(getTagsFromRequest(request.getParameter("tags")));
        newContact.setUser(getUserFromRequest(request.getParameter("responsible")));
        newContact.setPost(request.getParameter("position"));
        newContact.setPhoneType(PhoneType.valueOf(request.getParameter("phonetype")));
        newContact.setPhone(request.getParameter("phonenumber"));
        newContact.setEmail(request.getParameter("email"));
        newContact.setSkype(request.getParameter("skype"));
        newContact.setCompany(getCompanyFromRequest(request));


        try {
            GenericDao<Contact>  contactDao = dao.getDao(connection,Contact.class);
            Contact returnedContact = contactDao.create(newContact);
            System.out.println("id - "+returnedContact.getId());
            System.out.println(returnedContact.getName());
            System.out.println(returnedContact.getPost());
            Set<Tag> tags = returnedContact.getTags();
            if(tags!=null){
                for(Tag tag: tags){
                    System.out.println(tag.getName());
                }
            }
            System.out.println(returnedContact.getPhoneType());
            System.out.println(returnedContact.getPhone());
            System.out.println(returnedContact.getEmail());
            System.out.println(returnedContact.getSkype());
            if(returnedContact.getCompany()!=null){
                System.out.println(returnedContact.getCompany().getName());
                System.out.println(returnedContact.getCompany().getPhoneNumber());
                System.out.println(returnedContact.getCompany().getWeb());
            }

        } catch (DataBaseException e) {
            e.printStackTrace();
        }

    }

    private User getUserFromRequest(String str){
        User result = new User();
        return result;
    }

    private Set<Tag> getTagsFromRequest(String str){
        Set<Tag> result = new HashSet<>();
        String[] temp = str.split(" ");
        for(String stringTag:temp){
            Tag tag = new Tag();
            tag.setName(stringTag);
            result.add(tag);
        }
        return result;
    }

    private Company getCompanyFromRequest(HttpServletRequest request){
        Company result = new Company();
        result.setName(request.getParameter("newcompanyname"));
        result.setPhoneNumber(request.getParameter("newcompanyphone"));
        result.setEmail(request.getParameter("newcompanyemail"));
        String url = request.getParameter("newcompanywebaddress");
        try {
            result.setWeb(new URL("https://www.google.com.ua"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        result.setAdress(request.getParameter("newcompanyaddress"));
        GenericDao<Company>  companyDao = null;
        try {
            companyDao = dao.getDao(connection,Company.class);
            result = companyDao.create(result);
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        return result;
    }

}
