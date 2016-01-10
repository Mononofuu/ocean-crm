package com.becomejavasenior.verification;

import com.becomejavasenior.ContactService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.impl.ContactServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="new_contact_verify", urlPatterns = "/new_contact_verify")
public class NewContactVerifyServlet extends AbstractVerifyServlet{
    private Logger logger = LogManager.getLogger(NewCompanyVerifyServlet.class);
    private static ContactService contactService = new ContactServiceImpl();

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
            logger.error(e);
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
            result.putAll(new NewDealVerifyServlet().verifyData(req));
        }
        if(checkDealFieldContent(req)){
            result.putAll(new NewTaskVerifyServlet().verifyData(req));
        }

        return result;
    }

    private boolean checkTaskFieldContent(HttpServletRequest req){
        if(!"".equals(req.getParameter("dealtype"))){
            return true;
        }else if(!"".equals(req.getParameter("budget"))){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkDealFieldContent(HttpServletRequest req){
        if(!"".equals(req.getParameter("taskresponsible"))){
            return true;
        }else if(!"".equals(req.getParameter("tasktype"))){
            return true;
        }else{
            return false;
        }
    }
}
