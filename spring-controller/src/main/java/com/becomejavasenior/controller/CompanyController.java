package com.becomejavasenior.controller;

import com.becomejavasenior.Company;
import com.becomejavasenior.CompanyService;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.validation.PatternValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping(value = "/companies")
public class CompanyController {
    private static final Logger LOGGER = LogManager.getLogger(CompanyController.class);
    private static final String NEW_COMPANY_NAME = "newcompanyname";
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> verifyCompanyFields(Model model, @RequestParam Map<String, String> parameters){
        return verifyData(parameters);
    }

    @RequestMapping(value = "/ajaxadd", method = RequestMethod.POST)
    @ResponseBody
    public Company addCompanyByAjax(Model model, @RequestParam Map<String, String> parameters)throws DataBaseException{
        Company result = new Company();
        result.setName(parameters.get("newcompanyname"));
        result.setPhoneNumber(parameters.get("newcompanyphone"));
        result.setEmail(parameters.get("newcompanyemail"));
        String url = parameters.get("newcompanywebaddress");
        try {
            result.setWeb(new URL(url));
        } catch (MalformedURLException e) {
            try {
                result.setWeb(new URL("http://" + url));
            } catch (MalformedURLException e1) {
                LOGGER.error("Unparseble URL", e);
            }
        }
        result.setAddress(parameters.get("newcompanyaddress"));
        return companyService.saveCompany(result);
    }

    private Map<String, String> verifyData(Map<String, String> parameters){
        Map<String, String> result = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle labels = ResourceBundle.getBundle("messages", locale);
        String name = parameters.get(NEW_COMPANY_NAME);
        try {
            if("".equals(name)){
                result.put(NEW_COMPANY_NAME, labels.getString("label.notempty"));
            }
            else if(companyService.findCompanyByName(name)!=null){
                result.put(NEW_COMPANY_NAME, labels.getString("label.companyexists"));
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        String phoneNumber = parameters.get("newcompanyphone");
        if(!PatternValidateUtil.validatePhone(phoneNumber)){
            result.put("newcompanyphone", labels.getString("label.incorrect.phone"));
        }
        String email = parameters.get("newcompanyemail");
        if(!"".equals(email)&&!PatternValidateUtil.validateEmail(email)){
            result.put("newcompanyemail", labels.getString("label.incorrect.email"));
        }
        String url = parameters.get("newcompanywebaddress");
        if(!"".equals(url)&&!PatternValidateUtil.validateURL(url)){
            result.put("newcompanywebaddress", labels.getString("label.incorrect.web"));
        }
        return result;
    }

}
