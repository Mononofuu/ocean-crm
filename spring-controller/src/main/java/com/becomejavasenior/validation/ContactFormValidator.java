package com.becomejavasenior.validation;

import com.becomejavasenior.Deal;
import com.becomejavasenior.ServiceException;
import com.becomejavasenior.Task;
import com.becomejavasenior.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class ContactFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(ContactFormValidator.class);
    @Autowired
    private ContactValidator contactValidator;
    @Autowired
    private DealValidator dealValidator;
    @Autowired
    private TaskValidator taskValidator;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ContactFormHandler.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){
        ContactFormHandler contactForm = (ContactFormHandler) o;
        if(contactForm.getTags()!=null&&!"".equals(contactForm.getTags())&&!PatternValidateUtil.validateTagsString(contactForm.getTags())){
            errors.rejectValue("tags", "label.incorrect.tags");
        }
        errors.setNestedPath("contact");
        contactValidator.validate(contactForm.getContact(), errors);
        Deal deal = contactForm.getDeal();
        if(deal.getBudget()!=0||deal.getStatus()!=null){
            errors.setNestedPath("deal");
            dealValidator.validate(deal, errors);
        }
        Task task = contactForm.getTask();
        if(task.getType()!=null||task.getUser()!=null||task.getDueTime()!=null){
            task.setSubject(contactForm.getContact());
            try {
                task.setUser(userService.findUserById(contactForm.getUserTaskId()));
            } catch (ServiceException e) {
                LOGGER.error(e);
            }
            errors.setNestedPath("task");
            taskValidator.validate(task, errors);
        }
        errors.setNestedPath("");
    }
}
