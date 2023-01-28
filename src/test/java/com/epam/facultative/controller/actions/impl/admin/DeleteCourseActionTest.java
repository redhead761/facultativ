package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.constants.ActionNameConstants.MANAGE_COURSES_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class DeleteCourseActionTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executeSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        doNothing().when(adminService).deleteCourse(anyInt());
        String path = new DeleteCourseAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(MANAGE_COURSES_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1", SORT, "1", SELECT_BY_CATEGORY, "1", SELECT_BY_TEACHER, "1", ORDER, "1"), path);
    }

    @Test
    void executeWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        doThrow(ValidateException.class).when(adminService).deleteCourse(anyInt());
        String path = new DeleteCourseAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(MANAGE_COURSES_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1", SORT, "1", SELECT_BY_CATEGORY, "1", SELECT_BY_TEACHER, "1", ORDER, "1"), path);
    }

    private void setUpReturn() {
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
        when(req.getParameter(SORT)).thenReturn("1");
        when(req.getParameter(ORDER)).thenReturn("1");
        when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
        when(req.getParameter(SELECT_BY_TEACHER)).thenReturn("1");
        when(req.getParameter(SELECT_BY_CATEGORY)).thenReturn("1");
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
    }

}