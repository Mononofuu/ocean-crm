package com.becomejavasenior.verification;

import com.becomejavasenior.ContactService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.impl.ContactServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="new_contact_verify", urlPatterns = "/new_contact_verify")
public class NewContactVerifyServlet extends AbstractVerifyServlet{
    private static final Logger LOGGER = LogManager.getLogger(NewCompanyVerifyServlet.class);
    @Autowired
    private ContactService contactService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected Map<String, String> verifyData(HttpServletRequest req){
        Map<String, String> result = new HashMap<>();
        String name = req.getParameter("name");
        if("".equals(name)){
            result.put("name", "Введите имя контакта");
        }else try {
            if(contactService.findContactByName(name)!=null){
                result.put("name", "Контакт уже существует в базе");
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        String phonenNumber = req.getParameter("phonenumber");
        if(!"".equals(phonenNumber)&&!checkString(phonenNumber, PHONE_PATTERN)){
            result.put("phonenumber", "Некоректный номер");
        }
        String email = req.getParameter("email");
        if(!"".equals(email)&&!checkString(email, EMAIL_PATTERN)){
            result.put("email", "Некоректный email");
        }
        String tags = req.getParameter("tags");
        if(!"".equals(tags)&&!checkString(tags, TAG_PATTERN)){
            result.put("tags", "Тэги могут содержать только цифры и буквы кирилицы и латиницы");
        }
        String skype = req.getParameter("skype");
        if(!"".equals(skype)&&!checkString(skype, SKYPE_PATTERN)){
            result.put("skype", "Некоректный skype логин");
        }
        if(checkTaskFieldContent(req)){
            result.putAll(new NewTaskVerifyServlet().verifyData(req));
        }
        if(checkDealFieldContent(req)){
            result.putAll(new NewDealVerifyServlet().verifyData(req));
        }

        return result;
    }

    private boolean checkTaskFieldContent(HttpServletRequest req){
        return !"".equals(req.getParameter("taskresponsible"))||!"".equals(req.getParameter("tasktype"))||!"".equals(req.getParameter("tasktext"));
    }

    private boolean checkDealFieldContent(HttpServletRequest req){
        return !"".equals(req.getParameter("dealtype"))||!"".equals(req.getParameter("budget"))||!"".equals(req.getParameter("newdealname"));
    }
}
