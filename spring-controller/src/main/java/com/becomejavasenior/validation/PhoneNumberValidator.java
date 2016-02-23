package com.becomejavasenior.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */

@Component
public class PhoneNumberValidator extends PaternValidator {
    private static final String PHONE_PATTERN = "^([+]?[0-9\\s-\\(\\)]{3,25})*$";

    public PhoneNumberValidator() {
        super(Pattern.compile(PHONE_PATTERN));
    }
}
