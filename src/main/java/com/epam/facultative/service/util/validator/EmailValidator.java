package com.epam.facultative.service.util.validator;

import static com.epam.facultative.service.util.validator.Regex.*;

public class EmailValidator implements Validator {
    private static final EmailValidator INSTANCE = new EmailValidator();

    private EmailValidator() {
    }

    public static EmailValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(String email) {
        if (email == null) {
            return false;
        }
        if (!email.matches(EMAIL_PATTERN_REGEX)) {
            return false;
        }
        if (email.length() > 45) {
            return false;
        }
        return true;
    }
}
