package com.becomejavasenior.validation;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;
import com.becomejavasenior.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class DealValidator implements Validator {
    @Autowired
    DealService dealService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Deal.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Deal deal = (Deal) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "label.notempty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "label.notempty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "budget", "label.notempty");
    }
}
