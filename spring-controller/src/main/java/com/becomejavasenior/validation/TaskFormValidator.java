package com.becomejavasenior.validation;

import com.becomejavasenior.Task;
import com.becomejavasenior.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by kramar on 26.02.16.
 */

@Component
public class TaskFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(TaskFormValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return TaskFormHandler.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){
        TaskFormHandler taskForm = (TaskFormHandler) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "task.comment", "label.notempty");
        if(taskForm.getTask().getUser()==null) {
            errors.rejectValue("task.user", "label.notempty");
        }
        if(taskForm.getTask().getSubject()==null) {
            errors.rejectValue("task.subject", "label.notempty");
        }
    }

}
