package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.ASSIGN_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.ASSIGN_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AssignActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        doNothing().when(adminService).assigned(anyInt(), anyInt());
        String path = new AssignAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ASSIGN_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        doThrow(ValidateException.class).when(adminService).assigned(anyInt(), anyInt());
        String path = new AssignAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ASSIGN_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            when(req.getMethod()).thenReturn(GET);
            when(req.getParameter(COURSE_ID)).thenReturn("1");
            when(appContext.getGeneralService()).thenReturn(generalService);
            actionUtils.when(() -> setAllTeachers(req, generalService)).thenAnswer(invocation -> null);
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            String path = new AssignAction(appContext).execute(req, resp);
            assertEquals(ASSIGN_PAGE, path);
        }
    }

    private void setUpReturn() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(req.getParameter(TEACHER_ID)).thenReturn("2");
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
    }


}