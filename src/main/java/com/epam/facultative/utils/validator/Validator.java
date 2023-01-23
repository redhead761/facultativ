package com.epam.facultative.utils.validator;

import com.epam.facultative.exception.ValidateException;

import java.time.LocalDate;

import static com.epam.facultative.utils.validator.RegexConstants.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;

public final class Validator {

    private Validator() {
    }

    public static void validateLogin(String login) throws ValidateException {
        if (!login.matches(LOGIN_PATTERN_REGEX)) {
            throw new ValidateException(LOGIN_EXCEPTION_MESSAGE);
        }
    }

    public static void validatePassword(String password) throws ValidateException {
        if (!password.matches(PASSWORD_PATTERN_REGEX)) {
            throw new ValidateException(PASSWORD_EXCEPTION_MESSAGE);
        }
    }

    public static void validateEmail(String email) throws ValidateException {
        if (!email.matches(EMAIL_PATTERN_REGEX)) {
            throw new ValidateException(EMAIL_EXCEPTION_MESSAGE);
        }
    }

    public static void validateNameAndSurname(String name, String surname) throws ValidateException {
        if (!name.matches(NAME_PATTERN_REGEX) || !surname.matches(NAME_PATTERN_REGEX)) {
            throw new ValidateException(NAME_EXCEPTION_MESSAGE);
        }
    }

    public static void validateCategoryData(String title, String description) throws ValidateException {
        if (!title.matches(TITLE_PATTERN_REGEX) || !description.matches(DESCRIPTION_PATTERN_REGEX)) {
            throw new ValidateException(CATEGORY_DATA_EXCEPTION_MESSAGE);
        }
    }

    public static void validateCourseData(String title, String description, int duration) throws ValidateException {
        if (!title.matches(TITLE_PATTERN_REGEX) || !description.matches(DESCRIPTION_PATTERN_REGEX) || duration <= 0) {
            throw new ValidateException(COURSE_DATA_EXCEPTION_MESSAGE);
        }
    }

    public static void validateDate(LocalDate date) throws ValidateException {
        if (date == null || !date.isAfter(LocalDate.now())) {
            throw new ValidateException(DATE_EXCEPTION_MESSAGE);
        }
    }
}
