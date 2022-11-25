package com.epam.facultative.service.util.validator;

import static com.epam.facultative.service.util.validator.Regex.*;

public class CategoryDataValidator implements Validator {
    private static final CategoryDataValidator INSTANCE = new CategoryDataValidator();

    private CategoryDataValidator() {
    }

    public static CategoryDataValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(String title, String description) {
        if (title == null) {
            return false;
        }
        if (!title.matches(TITLE_PATTERN_REGEX)) {
            return false;
        }
        if (title.length() > 45) {
            return false;
        }
        if (description != null && description.length() > 255) {
            return false;
        }
        return true;
    }
}
