package com.epam.facultative.model.utils.validator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class containing regex for validator.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class RegexConstants {
    //Regex
    static final String TITLE_PATTERN_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!?@#$^&*()={}| ]{1,100}";
    static final String EMAIL_PATTERN_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    static final String LOGIN_PATTERN_REGEX = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{4,16}$";
    static final String PASSWORD_PATTERN_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    static final String NAME_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";
    static final String DESCRIPTION_PATTERN_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!?@#$^&*()={}| ]{0,400}";
}

