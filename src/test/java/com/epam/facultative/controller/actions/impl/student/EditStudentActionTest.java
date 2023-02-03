package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.EDIT_STUDENT_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.SUCCESSFUL;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_STUDENT_PROFILE_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class EditStudentActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final StudentService studentService = mock(StudentService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        when(studentService.updateStudent(isA(StudentDTO.class))).thenReturn(testServiceUtil.getStudentDTO());
        String path = new EditStudentAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(EDIT_STUDENT_ACTION), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        doThrow(ValidateException.class).when(studentService).updateStudent(isA(StudentDTO.class));
        String path = new EditStudentAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(EDIT_STUDENT_ACTION), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            String path = new EditStudentAction(appContext).execute(req, resp);
            assertEquals(EDIT_STUDENT_PROFILE_PAGE, path);
        }


    }

    private void setUpReturn() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(LOGIN)).thenReturn(LOGIN);
        when(req.getParameter(NAME)).thenReturn(NAME);
        when(req.getParameter(SURNAME)).thenReturn(SURNAME);
        when(req.getParameter(EMAIL)).thenReturn(EMAIL);
        when(req.getParameter(COURSE_NUMBER)).thenReturn("1");
        when(appContext.getStudentService()).thenReturn(studentService);
        when(req.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(USER)).thenReturn(testServiceUtil.getStudentDTO());
    }
}