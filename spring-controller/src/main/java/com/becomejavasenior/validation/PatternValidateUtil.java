package com.becomejavasenior.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class PatternValidateUtil {
    private static final String PHONE_PATTERN = "^([+]?[0-9\\s-\\(\\)]{3,25})*$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String URL_PATTERN = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,4})$";
    private static final String TAG_PATTERN = "^[а-яА-ЯёЁa-zA-Z0-9# ]+$";
    private static final String BUDGET_PATTERN = "\\-?\\d+(\\.\\d{0,})?";
    private static final String SKYPE_PATTERN = "^[a-zA-Z0-9,\\._-]{6,32}$";

    public static boolean validateEmail(String email){
        return validate(EMAIL_PATTERN, email);
    }

    public static boolean validatePhone(String phone){
        return validate(PHONE_PATTERN, phone);
    }

    public static boolean validateSkype(String skype){
        return validate(SKYPE_PATTERN, skype);
    }

    public static boolean validateURL(String url){
        return validate(URL_PATTERN, url);
    }

    public static boolean validateBudget(String budget){
        return validate(BUDGET_PATTERN, budget);
    }

    public static boolean validateTagsString(String tags){
        return validate(TAG_PATTERN, tags);
    }

    private static boolean validate(String patternString, String value){
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
