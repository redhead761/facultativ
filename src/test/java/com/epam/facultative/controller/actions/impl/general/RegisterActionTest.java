package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.AUTH_ACTION;
import static com.epam.facultative.controller.constants.ActionNameConstants.REGISTER_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.REGISTER_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class RegisterActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final StudentService studentService = mock(StudentService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(PASSWORD);
        doNothing().when(studentService).addStudent(isA(StudentDTO.class));
        String path = new RegisterAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(AUTH_ACTION), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(PASSWORD);
        doThrow(ValidateException.class).when(studentService).addStudent(isA(StudentDTO.class));
        String path = new RegisterAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(REGISTER_ACTION), path);
    }

    @Test
    void executePostWithInvalidateRepeatPassword() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(REPEAT_PASSWORD);
        doNothing().when(studentService).addStudent(isA(StudentDTO.class));
        String path = new RegisterAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(REGISTER_ACTION), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            String path = new RegisterAction(appContext).execute(req, resp);
            assertEquals(REGISTER_PAGE, path);
        }
    }

    private void setUpReturn() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(LOGIN)).thenReturn(LOGIN);
        when(req.getParameter(PASSWORD)).thenReturn(PASSWORD);
        when(req.getParameter(NAME)).thenReturn(NAME);
        when(req.getParameter(SURNAME)).thenReturn(SURNAME);
        when(req.getParameter(EMAIL)).thenReturn(EMAIL);
        when(req.getParameter(COURSE_NUMBER)).thenReturn("1");
        when(appContext.getStudentService()).thenReturn(studentService);
        when(req.getSession()).thenReturn(httpSession);
    }
}