package com.epam.facultative.service.util.validator;

import static com.epam.facultative.service.util.validator.Regex.*;

public class NameValidator implements Validator {
    private static final NameValidator INSTANCE = new NameValidator();

    private NameValidator() {
    }

    public static NameValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(String name, String surname) {
        if (name == null || surname == null) {
            return false;
        }
        if (!name.matches(NAME_PATTERN_REGEX)) {
            return false;
        }
        if (!surname.matches((NAME_PATTERN_REGEX))) {
            return false;
        }
        if (name.length() > 45 || surname.length() > 45) {
            return false;
        }
        return true;
    }
}
