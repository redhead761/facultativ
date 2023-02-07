package com.epam.facultative.model.utils.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class containing message with not validate parameter
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateExceptionMessageConstants {
    static final String LOGIN_NO_VALIDATE_MESSAGE = "You entered incorrect login, login must contains 4-16 characters";
    static final String PASSWORD_NO_VALIDATE_MESSAGE = "You entered incorrect password, password must contains 8-20 characters";
    static final String NAME_NO_VALIDATE_MESSAGE = "You entered incorrect name or surname, name or surname must contains 1-30 characters";
    static final String EMAIL_NO_VALIDATE_MESSAGE = "You entered incorrect email, email must contains @";
    static final String DATE_NO_VALIDATE_MESSAGE = "You entered incorrect date";
    static final String TITLE_NO_VALIDATE_MESSAGE = "You entered incorrect title, title must contains 1-100 characters";
    static final String DESCRIPTION_NO_VALIDATE_MESSAGE = "You entered incorrect description, description must contains 0-500 characters";
    public static final String COMING_SOON_NO_VALIDATE_MESSAGE = "coming.soon.no.validate";
    public static final String IN_PROCESS_NO_VALIDATE_MESSAGE = "in.process.no.validate";
    public static final String COMPLETED_NO_VALIDATE_MESSAGE = "completed.no.validate";
    public static final String NOT_NULL_MESSAGE = "Status or start date can't be null";
}
