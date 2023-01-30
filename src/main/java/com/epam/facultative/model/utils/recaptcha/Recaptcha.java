package com.epam.facultative.model.utils.recaptcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

import static com.epam.facultative.model.utils.recaptcha.CaptchaConstants.*;

/**
 * Validate Google ReCaptcha
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class Recaptcha {

    private static final Logger logger = LoggerFactory.getLogger(Recaptcha.class);
    private final String captchaURL;
    private final String secret;
    private final String userAgent;
    private final String acceptLanguage;

    public Recaptcha(Properties properties) {
        captchaURL = properties.getProperty(CAPTCHA_URL);
        secret = properties.getProperty(CAPTCHA_SECRET);
        userAgent = properties.getProperty(CAPTCHA_USER_AGENT);
        acceptLanguage = properties.getProperty(CAPTCHA_ACCEPT_LANGUAGE);
    }

    /**
     * Checks if user is human. Setups and connects to Google ReCaptcha services. Sends User's captcha result.
     * Gets response and checks if it matches. Disable captcha if Google is down.
     *
     * @param gRecaptchaResponse get it from controller
     */
    public boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try {
            URL url = new URL(captchaURL);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            setUpConnection(con);
            write(gRecaptchaResponse, con);
            StringBuilder response = getResponse(con);
            return isCaptchaPassed(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    private boolean isCaptchaPassed(StringBuilder response) {
        JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        return jsonObject.getBoolean("success");
    }

    private StringBuilder getResponse(HttpsURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private void write(String gRecaptchaResponse, HttpsURLConnection con) throws IOException {
        String postParams = "secret=" + secret + "&response="
                + gRecaptchaResponse;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
    }

    private void setUpConnection(HttpsURLConnection con) throws ProtocolException {
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", userAgent);
        con.setRequestProperty("Accept-Language", acceptLanguage);
    }
}
