package com.epam.facultative.service.util.validator;

import static com.epam.facultative.service.util.validator.Regex.*;

public class CourseDataValidator implements Validator {
    private static final CourseDataValidator INSTANCE = new CourseDataValidator();

    private CourseDataValidator() {
    }

    public static CourseDataValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(String login, String description, int duration) {
        if (login == null) {
            return false;
        }
        if (!login.matches(TITLE_PATTERN_REGEX)) {
            return false;
        }
        if (login.length() > 45) {
            return false;
        }
        if (description != null && description.length() > 255) {
            return false;
        }
        if (duration <= 0) {
            return false;
        }
        return true;
    }
}
