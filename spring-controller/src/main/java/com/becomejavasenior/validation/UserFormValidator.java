package com.becomejavasenior.validation;

import com.becomejavasenior.ServiceException;
import com.becomejavasenior.User;
import com.becomejavasenior.UserService;
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
public class UserFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(UserFormValidator.class);

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PhoneNumberValidator phoneNumberValidator;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "label.notempty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "label.notempty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "label.notempty");
        try {
            if(userService.getUserByLogin(user.getLogin())!=null){
                errors.rejectValue("login", "label.userallreadyexists");
            }
        } catch (ServiceException e) {
            errors.rejectValue("login", "label.errorreadingfromdb");
            LOGGER.error(e);
        }
        if(!"".equals(user.getEmail())&&!emailValidator.valid(user.getEmail())){
            errors.rejectValue("email", "label.incorrect.email");
        }
        if(user.getPhoneHome()!=null&&!phoneNumberValidator.valid(user.getPhoneHome())){
            errors.rejectValue("phoneHome", "label.incorrect.phone");
        }
        if(user.getPhoneWork()!=null&&!phoneNumberValidator.valid(user.getPhoneWork())){
            errors.rejectValue("phoneWork", "label.incorrect.phone");
        }
    }
}
