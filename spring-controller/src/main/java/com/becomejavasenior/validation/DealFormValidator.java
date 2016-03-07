package com.becomejavasenior.validation;

import com.becomejavasenior.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Component
public class DealFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(DealFormValidator.class);
    @Autowired
    private DealValidator dealValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return DealFormHandler.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DealFormHandler dealForm = (DealFormHandler) o;
        Deal deal = dealForm.getDeal();
        errors.setNestedPath("deal");
        dealValidator.validate(deal, errors);
        errors.setNestedPath("");
    }
}
