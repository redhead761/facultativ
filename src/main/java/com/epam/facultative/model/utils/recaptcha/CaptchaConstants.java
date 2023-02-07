package com.epam.facultative.model.utils.recaptcha;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Keyholder for properties to configure captcha
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaptchaConstants {
    public static final String CAPTCHA_URL = "captcha.url";
    public static final String CAPTCHA_SECRET = "captcha.secret";
    public static final String CAPTCHA_USER_AGENT = "user.agent";
    public static final String CAPTCHA_ACCEPT_LANGUAGE = "accept.language";
}
