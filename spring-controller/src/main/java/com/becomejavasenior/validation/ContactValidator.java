package com.becomejavasenior.validation;

import com.becomejavasenior.Contact;
import com.becomejavasenior.ContactService;
import com.becomejavasenior.DataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class ContactValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(ContactValidator.class);
    @Autowired
    private ContactService contactService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Contact.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contact contact = (Contact) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "label.notempty");
        try {
            if(contactService.findContactByName(contact.getName())!=null){
                errors.rejectValue("name", "label.contactallreadyexists");
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        if(contact.getEmail()!=null&&!"".equals(contact.getEmail())&&!PatternValidateUtil.validateEmail(contact.getEmail())){
            errors.rejectValue("email", "label.incorrect.email");
        }
        if(contact.getPhone()!=null&&!PatternValidateUtil.validatePhone(contact.getPhone())){
            errors.rejectValue("phone", "label.incorrect.phone");
        }
        if(contact.getSkype()!=null&&!"".equals(contact.getSkype())&&!PatternValidateUtil.validateSkype(contact.getSkype())){
            errors.rejectValue("skype", "label.incorrect.skype");
        }
    }
}
