package com.becomejavasenior.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class PaternValidator {
    private Pattern pattern;
    private Matcher matcher;

    public PaternValidator(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean valid(final String str) {
        matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
