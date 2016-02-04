package com.becomejavasenior;

import com.becomejavasenior.impl.CompanyServiceImpl;
import com.becomejavasenior.impl.ContactServiceImpl;
import com.becomejavasenior.interfacedao.*;
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
import java.util.*;

/**
 * Created by Peter on 22.12.2015.
 */
@WebServlet("/dealedit")
public class DealEditServlet extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(DealController.class);
    @Autowired
    private DealService dealService;
    @Autowired
    private DealContactDAO dealContactDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CurrencyDAO currencyDAO;
    @Autowired
    private DealStatusDAO dealStatusDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private TaskDAO taskDAO;
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private PhoneTypeDAO phoneTypeDAO;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String requestString = "";
        String action = request.getParameter("action");
        switch (action) {
            case "contactupdate":
                try {
                    int dealId = Integer.parseInt(request.getParameter("dealid"));
                    int contactId = Integer.parseInt(request.getParameter("contactid"));
                    String submitname = request.getParameter("button_updatecontact" + contactId);
                    switch (submitname) {
                        case "companyedit":
                            requestString = "/companyedit?action=edit&id=" + request.getParameter("contactcompany" + contactId);
                            request.setAttribute("backurl", "/dealedit?action=edit&id=" + dealId);
                            break;
                        case "save":
                            ContactService contactService = new ContactServiceImpl();
                            Contact contact = contactService.findContactById(contactId);
                            contact.setName(request.getParameter("contactname" + contactId));
                            CompanyService companyService = new CompanyServiceImpl();
                            Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("contactcompany" + contactId)));
                            contact.setCompany(company);
                            contact.setPost(request.getParameter("contactpost" + contactId));
                            contact.setPhoneType(PhoneType.valueOf(request.getParameter("contactphonetype" + contactId)));
                            contact.setPhone(request.getParameter("contactphone" + contactId));
                            contact.setEmail(request.getParameter("contactemail" + contactId));
                            contact.setSkype(request.getParameter("contactskype" + contactId));
                            contactService.saveContact(contact);
                            LOGGER.info("Contact updated:");
                            LOGGER.info(contact.getId());
                            LOGGER.info(contact.getName());
                            LOGGER.info(contact.getCompany().toString());
                            LOGGER.info(contact.getPost());
                            LOGGER.info(contact.getPhone());
                            LOGGER.info(contact.getPhoneType());
                            LOGGER.info(contact.getUser());
                            LOGGER.info(contact.getEmail());
                            LOGGER.info(contact.getSkype());
                            requestString = "/dealedit?action=edit&id=" + request.getParameter("dealid");
                            break;
                        case "delete":
                            dealContactDAO.deleteDealContact(dealId, contactId);
                            LOGGER.info("deal contact deleteded:");
                            LOGGER.info("deal " + dealId);
                            LOGGER.info("contact " + contactId);
                            Deal deal = dealService.findDealById(dealId);
                            deal.setMainContact(null);
                            dealService.saveDeal(deal);
                            LOGGER.info("main contact deleted");
                            requestString = "/dealedit?action=edit&id=" + request.getParameter("dealid");
                            break;
                    }
                } catch (DataBaseException e) {
                    LOGGER.error("Error while updating contact");
                    LOGGER.catching(e);
                }
                request.getRequestDispatcher(requestString).forward(request, response);
                break;
            case "update":
                String submitname = request.getParameter("submit");
                if (submitname != null) {
                    switch (submitname) {
                        case "company":
                            requestString = "/companyedit?action=edit&id=" + request.getParameter("company");
                            break;
                        case "contactmain":
                            requestString = "/contactedit?action=edit&id=" + request.getParameter("maincontact");
                            break;
                        case "deal":
                            try {
                                int id = Integer.parseInt(request.getParameter("id"));
                                Deal deal = dealService.findDealById(id);
                                deal.setName(request.getParameter("name"));
                                CompanyService companyService = new CompanyServiceImpl();
                                Company company = companyService.findCompanyById(Integer.parseInt(request.getParameter("company")));
                                deal.setDealCompany(company);
                                ContactService contactService = new ContactServiceImpl();
                                int mainContactId = Integer.parseInt(request.getParameter("maincontact"));
                                Contact mainContact = null;
                                if (mainContactId != 0) {
                                    mainContact = contactService.findContactById(mainContactId);
                                }
                                deal.setMainContact(mainContact);
                                Tag tag;
                                Set<Tag> set = new HashSet<Tag>();
                                String tags = request.getParameter("tags").replaceAll(" +", " ").trim();
                                List<String> tagList = Arrays.asList(tags.split(" "));
                                for (String tagName : tagList) {
                                    tag = new Tag();
                                    tag.setName(tagName);
                                    set.add(tag);
                                }
                                deal.setTags(set);
                                User user = userDAO.read(Integer.parseInt(request.getParameter("user")));
                                deal.setResponsible(user);
                                deal.setBudget(Integer.parseInt(request.getParameter("budget")));
                                Currency currency = currencyDAO.read(Integer.parseInt(request.getParameter("currency")));
                                deal.setCurrency(currency);
                                DealStatus dealStatus = dealStatusDAO.read(Integer.parseInt(request.getParameter("dealstatus")));
                                deal.setStatus(dealStatus);
                                dealService.saveDeal(deal);
                                LOGGER.info("deal updated:");
                                LOGGER.info(deal.getName());
                                LOGGER.info(deal.getDealCompany());
                                LOGGER.info(deal.getMainContact());
                                LOGGER.info(deal.getTags());
                                LOGGER.info(deal.getUser());
                                LOGGER.info(deal.getBudget());
                                LOGGER.info(deal.getCurrency());
                                LOGGER.info(deal.getStatus());
                            } catch (DataBaseException e) {
                                LOGGER.error("Error while updating deal");
                                LOGGER.catching(e);
                            }
                            request.getRequestDispatcher("/dealedit?action=edit&id=" + request.getParameter("id")).forward(request, response);
                            break;
                        case "dealcontactadd":
                            try {
                                int id = Integer.parseInt(request.getParameter("id"));
                                ContactService contactService = new ContactServiceImpl();
                                Contact contact = contactService.findContactById(Integer.parseInt(request.getParameter("newcontact")));
                                Subject subject = subjectDAO.read(id);
                                DealContact dealContact = new DealContact();
                                dealContact.setDeal(dealService.findDealById(id));
                                dealContact.setContact(contact);
                                dealContactDAO.create(dealContact);
                                LOGGER.info("deal updated:");
                                LOGGER.info(subject.getId());
                                LOGGER.info(subject.getName());
                                LOGGER.info("add contact:");
                                LOGGER.info(contact.getId());
                                LOGGER.info(contact.getName());
                            } catch (DataBaseException e) {
                                LOGGER.error("Error while updating deal");
                                LOGGER.catching(e);
                            }
                            request.getRequestDispatcher("/dealedit?action=edit&id=" + request.getParameter("id")).forward(request, response);
                            break;
                    }
                    if (!"".equals(requestString)) {
                        request.setAttribute("backurl", "/dealedit?action=edit&id=" + request.getParameter("id"));
                        request.getRequestDispatcher(requestString).forward(request, response);
                    }
                }
                break;
            case "edit":
                try {
                    Deal deal = dealService.findDealById(getId(request));
                    request.setAttribute("deal", deal);

                    List<DealStatus> dealStatusList = dealStatusDAO.readAll();
                    request.setAttribute("deal_statuses", dealStatusList);

                    List<User> userList = userDAO.readAll();
                    request.setAttribute("users", userList);

                    CompanyService companyService = new CompanyServiceImpl();
                    List<Company> companyList = companyService.findCompaniesLite();
                    request.setAttribute("companies", companyList);

                    ContactService contactService = new ContactServiceImpl();
                    List<Contact> contactList = contactService.findContactsLite();
                    request.setAttribute("contacts", contactList);

                    List<PhoneType> phoneTypeList = phoneTypeDAO.readAll();
                    request.setAttribute("phonetypes", phoneTypeList);

                    List<Contact> dealContactList = dealContactDAO.getAllContactsBySubjectId(deal.getId());
                    request.setAttribute("dealcontacts", dealContactList);

                    List<Tag> tagList = tagDAO.readAllSubjectTags(deal.getId());
                    StringBuilder sb = new StringBuilder();
                    for (Tag tag : tagList) {
                        sb.append(tag.getName() + " ");
                    }
                    request.setAttribute("tags", sb.toString());

                    List<Currency> currencyList = currencyDAO.readAll();
                    request.setAttribute("currencies", currencyList);

                    List<Comment> commentList = commentDAO.getAllCommentsBySubjectId(deal.getId());
                    request.setAttribute("comments", commentList);

                    List<Task> taskList = taskDAO.getAllTasksBySubjectId(deal.getId());
                    request.setAttribute("tasks", taskList);

                } catch (DataBaseException e) {
                    LOGGER.error(e);                }
                request.getRequestDispatcher("jsp/dealedit.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}


