package com.epam.facultative.controller.app_context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppContextTest {
    private static final String PROPERTIES_FILE = "configuration.properties";

    @Test
    void testRightFile() {
        assertDoesNotThrow(() -> AppContext.createAppContext(PROPERTIES_FILE));
        AppContext appContext = AppContext.getAppContext();
        assertNotNull(appContext.getGeneralService());
        assertNotNull(appContext.getAdminService());
        assertNotNull(appContext.getTeacherService());
        assertNotNull(appContext.getStudentService());
        assertNotNull(appContext.getEmailSender());
        assertNotNull(appContext.getPdfCreator());
        assertNotNull(appContext.getRecaptcha());

    }

    @Test
    void testNoCorrectFile() {
        assertThrows(NullPointerException.class, () -> AppContext.createAppContext("no.correct"));
    }
}