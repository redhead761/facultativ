package com.epam.facultative.service.util.validator;

import static com.epam.facultative.service.util.validator.Regex.*;

public class PasswordValidator implements Validator {

    private static final PasswordValidator INSTANCE = new PasswordValidator();

    private PasswordValidator() {
    }

    public static PasswordValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        if (!password.matches(PASSWORD_PATTERN_REGEX)) {
            return false;
        }
        if (password.length() > 45) {
            return false;
        }
        return true;
    }
}
