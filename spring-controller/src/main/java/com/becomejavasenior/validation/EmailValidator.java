package com.becomejavasenior.validation;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */

@Component
public class EmailValidator extends PaternValidator{
    protected static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        super(Pattern.compile(EMAIL_PATTERN));
    }
}
