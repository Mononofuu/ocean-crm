package com.becomejavasenior.validation;

import com.becomejavasenior.Company;
import com.becomejavasenior.CompanyService;
import com.becomejavasenior.DataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class CompanyValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(CompanyValidator.class);
    @Autowired
    private CompanyService companyService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Company.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Company company = (Company) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "label.notempty");
        try {
            if(companyService.findCompanyByName(company.getName())!=null){
                errors.rejectValue("name", "label.companyexists");
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);        }
        if(company.getPhoneNumber()!=null&&!"".equals(company.getPhoneNumber())&&PatternValidateUtil.validatePhone(company.getPhoneNumber())){
            errors.rejectValue("phoneNumber", "label.incorrect.phone");
        }
        if(company.getEmail()!=null&&!"".equals(company.getEmail())&&!PatternValidateUtil.validateEmail(company.getEmail())){
            errors.rejectValue("email", "label.incorrect.email");
        }
    }
}
