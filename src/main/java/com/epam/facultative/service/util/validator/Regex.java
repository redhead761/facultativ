package com.epam.facultative.service.util.validator;


class Regex {

    static final String TITLE_PATTERN_REGEX = "[а-яА-ЯйЙa-zA-Z0-9-_]$";
    static final String EMAIL_PATTERN_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]$";
    static final String LOGIN_PATTERN_REGEX = "[а-яА-ЯйЙa-zA-Z0-9-_]$";
    static final String PASSWORD_PATTERN_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)$";
    static final String NAME_PATTERN_REGEX = "[a-zA-Zа-яА-ЯйЙ]";
}
