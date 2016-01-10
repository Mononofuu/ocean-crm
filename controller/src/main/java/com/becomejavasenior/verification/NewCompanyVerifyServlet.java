package com.becomejavasenior.verification;

import com.becomejavasenior.CompanyService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.impl.CompanyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="newcompanyverify", urlPatterns = "/new_company_veryfy")
public class NewCompanyVerifyServlet extends AbstractVerifyServlet {
    private Logger logger = LogManager.getLogger(NewCompanyVerifyServlet.class);
    private static CompanyService companyService = new CompanyServiceImpl();

    protected Map<String, String> verifyData(HttpServletRequest req){
        Map<String, String> result = new HashMap<>();
        String name = req.getParameter("newcompanyname");
        try {
            if("".equals(name)){
                result.put("newcompanyname", "Введите имя компании");
            }
            else if(companyService.findCompanyByName(name)!=null){
                result.put("newcompanyname", "Компания уже есть в базе");
            }
        } catch (DataBaseException e) {
            logger.error(e);
        }
        String phoneNumber = req.getParameter("newcompanyphone");
        if(!checkString(phoneNumber, PHONE_PATTERN)){
            logger.info(phoneNumber);
            result.put("newcompanyphone", "Некоректный номер");
        }
        String email = req.getParameter("newcompanyemail");
        if(!"".equals(email)&&!checkString(email, EMAIL_PATTERN)){
            result.put("newcompanyemail", "Некоректный email");
        }
        String url = req.getParameter("newcompanywebaddress");
        if(!"".equals(url)&&!checkString(url,URL_PATTERN)){
            result.put("newcompanywebaddress", "Некоректный web адресс");
        }
        return result;
    }

}
