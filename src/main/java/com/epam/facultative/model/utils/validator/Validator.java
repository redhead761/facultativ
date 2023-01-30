package com.epam.facultative.model.utils.validator;

import com.epam.facultative.model.exception.ValidateException;

import java.time.LocalDate;

import static com.epam.facultative.model.utils.validator.ValidateExceptionMessageConstants.*;

/**
 * Validate parameter
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public final class Validator {

    private Validator() {
    }

    public static void validateLogin(String login) throws ValidateException {
        if (login == null || !login.matches(RegexConstants.LOGIN_PATTERN_REGEX)) {
            throw new ValidateException(LOGIN_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validatePassword(String password) throws ValidateException {
        if (password == null || !password.matches(RegexConstants.PASSWORD_PATTERN_REGEX)) {
            throw new ValidateException(PASSWORD_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateEmail(String email) throws ValidateException {
        if (email == null || !email.matches(RegexConstants.EMAIL_PATTERN_REGEX)) {
            throw new ValidateException(EMAIL_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateName(String name) throws ValidateException {
        if (name == null || !name.matches(RegexConstants.NAME_PATTERN_REGEX)) {
            throw new ValidateException(NAME_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateTitle(String title) throws ValidateException {
        if (title == null || !title.matches(RegexConstants.TITLE_PATTERN_REGEX)) {
            throw new ValidateException(TITLE_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateDescription(String description) throws ValidateException {
        if (description == null || !description.matches(RegexConstants.DESCRIPTION_PATTERN_REGEX)) {
            throw new ValidateException(DESCRIPTION_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateDate(LocalDate date) throws ValidateException {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new ValidateException(DATE_NO_VALIDATE_MESSAGE);
        }
    }
}
