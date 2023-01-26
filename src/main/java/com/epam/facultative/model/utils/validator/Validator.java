package com.epam.facultative.model.utils.validator;

import com.epam.facultative.model.exception.ValidateException;

import java.time.LocalDate;

public final class Validator {

    private Validator() {
    }

    public static void validateLogin(String login) throws ValidateException {
        if (!login.matches(RegexConstants.LOGIN_PATTERN_REGEX)) {
            throw new ValidateException(ValidateExceptionMessageConstants.LOGIN_EXCEPTION_MESSAGE);
        }
    }

    public static void validatePassword(String password) throws ValidateException {
        if (!password.matches(RegexConstants.PASSWORD_PATTERN_REGEX)) {
            throw new ValidateException(ValidateExceptionMessageConstants.PASSWORD_EXCEPTION_MESSAGE);
        }
    }

    public static void validateEmail(String email) throws ValidateException {
        if (!email.matches(RegexConstants.EMAIL_PATTERN_REGEX)) {
            throw new ValidateException(ValidateExceptionMessageConstants.EMAIL_EXCEPTION_MESSAGE);
        }
    }

    public static void validateNameAndSurname(String name, String surname) throws ValidateException {
        if (!name.matches(RegexConstants.NAME_PATTERN_REGEX) || !surname.matches(RegexConstants.NAME_PATTERN_REGEX)) {
            throw new ValidateException(ValidateExceptionMessageConstants.NAME_EXCEPTION_MESSAGE);
        }
    }

    public static void validateCategoryData(String title, String description) throws ValidateException {
        if (!title.matches(RegexConstants.TITLE_PATTERN_REGEX) || !description.matches(RegexConstants.DESCRIPTION_PATTERN_REGEX)) {
            throw new ValidateException(ValidateExceptionMessageConstants.CATEGORY_DATA_EXCEPTION_MESSAGE);
        }
    }

    public static void validateCourseData(String title, String description, int duration) throws ValidateException {
        if (!title.matches(RegexConstants.TITLE_PATTERN_REGEX) || !description.matches(RegexConstants.DESCRIPTION_PATTERN_REGEX) || duration <= 0) {
            throw new ValidateException(ValidateExceptionMessageConstants.COURSE_DATA_EXCEPTION_MESSAGE);
        }
    }

    public static void validateDate(LocalDate date) throws ValidateException {
        if (date == null || !date.isBefore(LocalDate.now())) {
            throw new ValidateException(ValidateExceptionMessageConstants.DATE_EXCEPTION_MESSAGE);
        }
    }
}
