package com.epam.facultative.utils.validator;

class Constants {
    //Regex
    static final String TITLE_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- 0-9]{1,30}";
    static final String EMAIL_PATTERN_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    static final String LOGIN_PATTERN_REGEX = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{4,16}$";
    static final String PASSWORD_PATTERN_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    static final String NAME_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}";
    static final String DESCRIPTION_PATTERN_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={} ]{0,200}";

    //Exception messages
    static final String LOGIN_EXCEPTION_MESSAGE = "You entered incorrect login, login must ....";
    static final String PASSWORD_EXCEPTION_MESSAGE = "You entered incorrect password, password must...";
    static final String NAME_EXCEPTION_MESSAGE = "You entered incorrect name or surname, name or surname must ...";
    static final String EMAIL_EXCEPTION_MESSAGE = "You entered incorrect email, email must ....";
    static final String CATEGORY_DATA_EXCEPTION_MESSAGE = "You entered incorrect category data...";
    static final String COURSE_DATA_EXCEPTION_MESSAGE = "You entered incorrect course data...";
}
