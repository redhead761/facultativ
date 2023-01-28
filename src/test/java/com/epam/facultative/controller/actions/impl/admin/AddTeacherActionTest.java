package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.ADD_TEACHER_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.SUCCESSFUL;
import static com.epam.facultative.controller.constants.PageNameConstants.ADD_TEACHER_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class AddTeacherActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(PASSWORD);
        doNothing().when(adminService).addTeacher(isA(TeacherDTO.class));
        String path = new AddTeacherAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ADD_TEACHER_ACTION), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(PASSWORD);
        doThrow(ValidateException.class).when(adminService).addTeacher(isA(TeacherDTO.class));
        String path = new AddTeacherAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ADD_TEACHER_ACTION), path);
    }

    @Test
    void executePostWithInvalidateRepeatPassword() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(REPEAT_PASSWORD);
        doNothing().when(adminService).addTeacher(isA(TeacherDTO.class));
        String path = new AddTeacherAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ADD_TEACHER_ACTION), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            when(req.getMethod()).thenReturn(GET);
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            String path = new AddTeacherAction(appContext).execute(req, resp);
            assertEquals(ADD_TEACHER_PAGE, path);
        }
    }

    private void setUpReturn() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(LOGIN)).thenReturn(LOGIN);
        when(req.getParameter(PASSWORD)).thenReturn(PASSWORD);
        when(req.getParameter(NAME)).thenReturn(NAME);
        when(req.getParameter(SURNAME)).thenReturn(SURNAME);
        when(req.getParameter(EMAIL)).thenReturn(EMAIL);
        when(req.getParameter(DEGREE)).thenReturn(DEGREE);
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
    }
}