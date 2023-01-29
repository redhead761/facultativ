package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.constants.ActionNameConstants.SHOW_ALL_COURSES_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final StudentService studentService = mock(StudentService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executeSuccessful() throws ServiceException {
        setUpReturn();
        doNothing().when(studentService).enroll(anyInt(), anyInt());
        String path = new EnrollAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(SHOW_ALL_COURSES_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1", SORT, "sort", SELECT_BY_CATEGORY, "1", SELECT_BY_TEACHER, "1", ORDER, "order"), path);
    }

    @Test
    void executeWithValidateException() throws ServiceException {
        setUpReturn();
        doThrow(ServiceException.class).when(studentService).enroll(anyInt(), anyInt());
        String path = new EnrollAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(SHOW_ALL_COURSES_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1", SORT, "sort", SELECT_BY_CATEGORY, "1", SELECT_BY_TEACHER, "1", ORDER, "order"), path);
    }

    private void setUpReturn() {
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
        when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
        when(req.getParameter(SORT)).thenReturn(SORT);
        when(req.getParameter(ORDER)).thenReturn(ORDER);
        when(req.getParameter(SELECT_BY_CATEGORY)).thenReturn("1");
        when(req.getParameter(SELECT_BY_TEACHER)).thenReturn("1");
        when(req.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(USER)).thenReturn(testServiceUtil.getStudentDTO());
        when(appContext.getStudentService()).thenReturn(studentService);
    }

}