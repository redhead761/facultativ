package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.facultative.controller.constants.AttributeConstants.LANGUAGE;
import static com.epam.facultative.controller.constants.AttributeConstants.USER;
import static com.epam.facultative.controller.constants.PageNameConstants.AUTH_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogOutActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executeSuccessful() {
        when(req.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(USER)).thenReturn(testServiceUtil.getAdminDTO());
        when(httpSession.getAttribute(LANGUAGE)).thenReturn("en");
        when(req.getSession(true)).thenReturn(httpSession);
        String path = new LogOutAction().execute(req, resp);
        assertEquals(AUTH_PAGE, path);
    }
}