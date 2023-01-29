package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.ActionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.AttributeConstants.ROLE;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.entities.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class MyCabinetActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executeAdmin() {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getSession()).thenReturn(httpSession);
            when(httpSession.getAttribute(ROLE)).thenReturn(ADMIN);
            String path = new MyCabinetAction().execute(req, resp);
            assertEquals(ADMIN_PROFILE_PAGE, path);
        }
    }

    @Test
    void executeTeacher() {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getSession()).thenReturn(httpSession);
            when(httpSession.getAttribute(ROLE)).thenReturn(TEACHER);
            String path = new MyCabinetAction().execute(req, resp);
            assertEquals(TEACHER_PROFILE_PAGE, path);
        }
    }

    @Test
    void executeStudent() {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getSession()).thenReturn(httpSession);
            when(httpSession.getAttribute(ROLE)).thenReturn(STUDENT);
            String path = new MyCabinetAction().execute(req, resp);
            assertEquals(STUDENT_PROFILE_PAGE, path);
        }
    }
}