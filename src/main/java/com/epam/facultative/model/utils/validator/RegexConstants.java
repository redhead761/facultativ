package com.epam.facultative.model.utils.validator;

class RegexConstants {
    //Regex
    static final String TITLE_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\"\"#№]{1,100}";
    static final String EMAIL_PATTERN_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    static final String LOGIN_PATTERN_REGEX = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{4,16}$";
    static final String PASSWORD_PATTERN_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    static final String NAME_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}";
    static final String DESCRIPTION_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''`\'#№?!]{0,500}";
}

