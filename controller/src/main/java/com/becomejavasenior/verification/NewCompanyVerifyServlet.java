package com.becomejavasenior.verification;

import com.becomejavasenior.CompanyService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.impl.CompanyServiceImpl;
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
@WebServlet(name="newcompanyverify", urlPatterns = "/new_company_veryfy")
public class NewCompanyVerifyServlet extends AbstractVerifyServlet {
    private static final Logger LOGGER = LogManager.getLogger(NewCompanyVerifyServlet.class);
    private static final String NEW_COMPANY_NAME = "newcompanyname";
    @Autowired
    private CompanyService companyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    protected Map<String, String> verifyData(HttpServletRequest req){
        Map<String, String> result = new HashMap<>();
        String name = req.getParameter(NEW_COMPANY_NAME);
        try {
            if("".equals(name)){
                result.put(NEW_COMPANY_NAME, "Введите имя компании");
            }
            else if(companyService.findCompanyByName(name)!=null){
                result.put(NEW_COMPANY_NAME, "Компания уже есть в базе");
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        String phoneNumber = req.getParameter("newcompanyphone");
        if(!checkString(phoneNumber, PHONE_PATTERN)){
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
