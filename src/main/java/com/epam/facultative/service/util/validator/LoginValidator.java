package com.epam.facultative.service.util.validator;

import static com.epam.facultative.service.util.validator.Regex.*;

public class LoginValidator implements Validator {
    private static final LoginValidator INSTANCE = new LoginValidator();

    private LoginValidator() {
    }

    public static LoginValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(String login) {
        if (login == null) {
            return false;
        }
        if (!login.matches(LOGIN_PATTERN_REGEX)) {
            return false;
        }
        if (login.length() > 45) {
            return false;
        }
        return true;
    }
}
