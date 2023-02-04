package com.epam.facultative.model.utils.validator;

import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.entities.Status;
import com.epam.facultative.model.exception.ValidateException;

import java.time.LocalDate;

import static com.epam.facultative.model.entities.Status.*;
import static com.epam.facultative.model.utils.validator.RegexConstants.*;
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
        if (login == null || !login.matches(LOGIN_PATTERN_REGEX)) {
            throw new ValidateException(LOGIN_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validatePassword(String password) throws ValidateException {
        if (password == null || !password.matches(PASSWORD_PATTERN_REGEX)) {
            throw new ValidateException(PASSWORD_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateEmail(String email) throws ValidateException {
        if (email == null || !email.matches(EMAIL_PATTERN_REGEX)) {
            throw new ValidateException(EMAIL_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateName(String name) throws ValidateException {
        if (name == null || !name.matches(NAME_PATTERN_REGEX)) {
            throw new ValidateException(NAME_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateTitle(String title) throws ValidateException {
        if (title == null || !title.matches(TITLE_PATTERN_REGEX)) {
            throw new ValidateException(TITLE_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateDescription(String description) throws ValidateException {
        if (description == null || !description.matches(DESCRIPTION_PATTERN_REGEX)) {
            throw new ValidateException(DESCRIPTION_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateStartDate(LocalDate date) throws ValidateException {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new ValidateException(DATE_NO_VALIDATE_MESSAGE);
        }
    }

    public static void validateStatus(CourseDTO courseDTO) throws ValidateException {
        Status status = courseDTO.getStatus();
        LocalDate startDate = courseDTO.getStartDate();

        if (status.equals(COMING_SOON) && startDate.isBefore(LocalDate.now())) {
            throw new ValidateException(COMING_SOON_NO_VALIDATE_MESSAGE);
        }

        if (status.equals(IN_PROCESS) && startDate.isAfter(LocalDate.now())) {
            throw new ValidateException(IN_PROCESS_NO_VALIDATE_MESSAGE);
        }

        if (status.equals(COMPLETED) && startDate.isAfter(LocalDate.now())) {
            throw new ValidateException((COMPLETED_NO_VALIDATE_MESSAGE));
        }

    }


}
