package com.becomejavasenior.validation;

import com.becomejavasenior.Task;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class TaskValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return Task.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Task task = (Task) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", "label.notempty");
        if(task.getUser()==null){
            errors.rejectValue("user", "label.responsiblemissed");
        }
        if(task.getSubject()==null){
            errors.rejectValue("subject", "label.subjectmissed");
        }
    }
}
