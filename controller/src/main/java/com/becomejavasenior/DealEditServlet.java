//package com.becomejavasenior;
//
//import com.becomejavasenior.impl.CompanyServiceImpl;
//import com.becomejavasenior.impl.ContactServiceImpl;
//import com.becomejavasenior.impl.DealServiceImpl;
//import com.becomejavasenior.interfacedao.CommentDAO;
//import com.becomejavasenior.interfacedao.DealContactDAO;
//import com.becomejavasenior.interfacedao.TagDAO;
//import com.becomejavasenior.interfacedao.TaskDAO;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
///**
// * Created by Peter on 22.12.2015.
// */
//@WebServlet("/dealedit")
//public class DealEditServlet extends HttpServlet {
//
//    private final static Logger logger = LogManager.getLogger(DealController.class);
//    private DaoFactory dao;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.process(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.process(req, resp);
//    }
//
//    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//
//        request.setCharacterEncoding("UTF-8");
//        String requestString = "";
//        String action = request.getParameter("action");
//        switch (action) {
//            case "contactupdate":
//                try {
//                    int dealId = Integer.parseInt(request.getParameter("dealid"));
//                    int contactId = Integer.parseInt(request.getParameter("contactid"));
//                    String submitname = request.getParameter("button_updatecontact"+contactId);
//                    switch (submitname){
//                        case "companyedit":
//                            requestString = "/companyedit?action=edit&id="+request.getParameter("contactcompany"+contactId);
//                            request.setAttribute("backurl", "/dealedit?action=edit&id="+dealId);
//                            break;
//                        case "save":
//                            ContactService contactService = new ContactServiceImpl();
//                            Contact contact = contactService.findContactById(contactId);
//                            dao = new PostgreSqlDaoFactory();
//                            contact.setName(request.getParameter("contactname"+contactId));
//                            CompanyService companyService = new CompanyServiceImpl();
//                            Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("contactcompany"+contactId)));
//                            contact.setCompany(company);
//                            contact.setPost(request.getParameter("contactpost"+contactId));
//                            contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype"+contactId)));
//                            contact.setPhone(request.getParameter("contactphone"+contactId));
//                            contact.setEmail(request.getParameter("contactemail"+contactId));
//                            contact.setSkype(request.getParameter("contactskype"+contactId));
//                            contactService.saveContact(contact);
//                            logger.info("Contact updated:");
//                            logger.info(contact.getId());
//                            logger.info(contact.getName());
//                            logger.info(contact.getCompany().toString());
//                            logger.info(contact.getPost());
//                            logger.info(contact.getPhone());
//                            logger.info(contact.getPhoneType());
//                            logger.info(contact.getUser());
//                            logger.info(contact.getEmail());
//                            logger.info(contact.getSkype());
//                            requestString = "/dealedit?action=edit&id="+request.getParameter("dealid");
//                            break;
//                        case "delete":
//                            dao = new PostgreSqlDaoFactory();
//                            DealContactDAO dealContactDAO = (DealContactDAO)dao.getDao(DealContact.class);
//                            dealContactDAO.deleteDealContact(dealId, contactId);
//                            logger.info("deal contact deleteded:");
//                            logger.info("deal " + dealId);
//                            logger.info("contact " + contactId);
//                            DealService dealService = new DealServiceImpl();
//                            Deal deal = dealService.findDealById(dealId);
//                            deal.setMainContact(null);
//                            dealService.saveDeal(deal);
//                            logger.info("main contact deleted");
//                            requestString = "/dealedit?action=edit&id="+request.getParameter("dealid");
//                            break;
//                    }
//                } catch (DataBaseException e) {
//                    logger.error("Error while updating contact");
//                    logger.catching(e);
//                }
//                request.getRequestDispatcher(requestString).forward(request, response);
//                break;
//            case "update":
//                String submitname = request.getParameter("submit");
//                if(submitname != null){
//                    switch (submitname){
//                        case "company":
//                            requestString = "/companyedit?action=edit&id="+request.getParameter("company");
//                            break;
//                        case "contactmain":
//                            requestString = "/contactedit?action=edit&id="+request.getParameter("maincontact");
//                            break;
//                        case "deal":
//                            try {
//                                dao = new PostgreSqlDaoFactory();
//                                DealService dealService = new DealServiceImpl();
//                                int id = Integer.parseInt(request.getParameter("id"));
//                                Deal deal = dealService.findDealById(id);
//                                deal.setName(request.getParameter("name"));
//                                CompanyService companyService = new CompanyServiceImpl();
//                                Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("company")));
//                                deal.setDealCompany(company);
//                                ContactService contactService = new ContactServiceImpl();
//                                int mainContactId = Integer.parseInt(request.getParameter("maincontact"));
//                                Contact mainContact = null;
//                                if(mainContactId != 0){
//                                    mainContact = contactService.findContactById(mainContactId);
//                                }
//                                deal.setMainContact(mainContact);
//                                Tag tag;
//                                Set<Tag> set = new HashSet<Tag>();
//                                String tags = request.getParameter("tags").replaceAll(" +"," ").trim();
//                                List<String> tagList= Arrays.asList(tags.split(" "));
//                                for (String tagName: tagList){
//                                    tag = new Tag();
//                                    tag.setName(tagName);
//                                    set.add(tag);
//                                }
//                                deal.setTags(set);
//                                GenericDao<User> userDao = dao.getDao(User.class);
//                                User user = userDao.read(Integer.parseInt(request.getParameter("user")));
//                                deal.setResponsible(user);
//                                deal.setBudget(Integer.parseInt(request.getParameter("budget")));
//                                GenericDao<Currency> currencyDao = dao.getDao(Currency.class);
//                                Currency currency = currencyDao.read(Integer.parseInt(request.getParameter("currency")));
//                                deal.setCurrency(currency);
//                                GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
//                                DealStatus dealStatus = dealStatusDao.read(Integer.parseInt(request.getParameter("dealstatus")));
//                                deal.setStatus(dealStatus);
//                                dealService.saveDeal(deal);
//                                logger.info("deal updated:");
//                                logger.info(deal.getName());
//                                logger.info(deal.getDealCompany());
//                                logger.info(deal.getMainContact());
//                                logger.info(deal.getTags());
//                                logger.info(deal.getUser());
//                                logger.info(deal.getBudget());
//                                logger.info(deal.getCurrency());
//                                logger.info(deal.getStatus());
//                            } catch (DataBaseException e) {
//                                logger.error("Error while updating deal");
//                                logger.catching(e);
//                            }
//                            request.getRequestDispatcher("/dealedit?action=edit&id="+request.getParameter("id")).forward(request, response);
//                            break;
//                        case "dealcontactadd":
//                            try {
//                                dao = new PostgreSqlDaoFactory();
//                                int id = Integer.parseInt(request.getParameter("id"));
//                                ContactService contactService = new ContactServiceImpl();
//                                Contact contact = contactService.findContactById(Integer.parseInt(request.getParameter("newcontact")));
//                                GenericDao<Subject> subjectDao = dao.getDao(Subject.class);
//                                Subject subject = subjectDao.read(id);
//                                DealContact dealContact = new DealContact();
////                                dealContact.setDeal((Deal) subject);
//                                dealContact.setDeal(new DealServiceImpl().findDealById(id));
//                                dealContact.setContact(contact);
//                                GenericDao<DealContact> dealContactDAO = dao.getDao(DealContact.class);
//                                dealContactDAO.create(dealContact);
//                                logger.info("deal updated:");
//                                logger.info(subject.getId());
//                                logger.info(subject.getName());
//                                logger.info("add contact:");
//                                logger.info(contact.getId());
//                                logger.info(contact.getName());
//                            } catch (DataBaseException e) {
//                                logger.error("Error while updating deal");
//                                logger.catching(e);
//                            }
//                            request.getRequestDispatcher("/dealedit?action=edit&id="+request.getParameter("id")).forward(request, response);
//                            break;
//                    }
//                    if (!"".equals(requestString)) {
//                        request.setAttribute("backurl", "/dealedit?action=edit&id="+request.getParameter("id"));
//                        request.getRequestDispatcher(requestString).forward(request, response);
//                    }
//                }
//                break;
//            case "edit":
//                    try {
//                        dao = new PostgreSqlDaoFactory();
//
//                        DealService dealService = new DealServiceImpl();
//                        Deal deal = dealService.findDealById(getId(request));
//                        request.setAttribute("deal", deal);
//
//                        GenericDao dealStatusDao = dao.getDao(DealStatus.class);
//                        List<DealStatus> dealStatusList = dealStatusDao.readAll();
//                        request.setAttribute("deal_statuses", dealStatusList);
//
//                        GenericDao userDao = dao.getDao(User.class);
//                        List<User> userList = userDao.readAll();
//                        request.setAttribute("users", userList);
//
//                        CompanyService companyService = new CompanyServiceImpl();
//                        List<Company> companyList = companyService.findCompaniesLite();
//                        request.setAttribute("companies", companyList);
//
//                        ContactService contactService = new ContactServiceImpl();
//                        List<Contact> contactList = contactService.findContactsLite();
//                        request.setAttribute("contacts", contactList);
//
//                        GenericDao phoneTypeDao = dao.getDao(PhoneType.class);
//                        List<PhoneType> phoneTypeList = phoneTypeDao.readAll();
//                        request.setAttribute("phonetypes", phoneTypeList);
//
//                        DealContactDAO dealContactDao = (DealContactDAO) dao.getDao(DealContact.class);
//                        List<Contact> dealContactList = dealContactDao.getAllContactsBySubjectId(deal.getId());
//                        request.setAttribute("dealcontacts", dealContactList);
//
//                        TagDAO tagDAO = (TagDAO) dao.getDao(Tag.class);
//                        List<Tag> tagList = tagDAO.readAllSubjectTags(deal.getId());
//                        StringBuilder sb = new StringBuilder();
//                        for(Tag tag: tagList){
//                            sb.append(tag.getName() + " ");
//                        }
//                        request.setAttribute("tags", sb.toString());
//
//                        GenericDao currencyDao = dao.getDao(Currency.class);
//                        List<Currency> currencyList = currencyDao.readAll();
//                        request.setAttribute("currencies", currencyList);
//
//                        CommentDAO commentDao = (CommentDAO) dao.getDao(Comment.class);
//                        List<Comment> commentList = commentDao.getAllCommentsBySubjectId(deal.getId());
//                        request.setAttribute("comments", commentList);
//
//                        TaskDAO taskDao = (TaskDAO) dao.getDao(Task.class);
//                        List<Task> taskList = taskDao.getAllTasksBySubjectId(deal.getId());
//                        request.setAttribute("tasks", taskList);
//
//                    } catch (DataBaseException e) {
//                        e.printStackTrace();
//                    }
//                    request.getRequestDispatcher("jsp/dealedit.jsp").forward(request, response);
//            break;
//        }
//    }
//
//    private int getId(HttpServletRequest request) {
//        String paramId = Objects.requireNonNull(request.getParameter("id"));
//        return Integer.valueOf(paramId);
//    }
//
//}
//
//
