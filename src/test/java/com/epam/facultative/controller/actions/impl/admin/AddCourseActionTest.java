package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.ADD_COURSE_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.SUCCESSFUL;
import static com.epam.facultative.controller.constants.PageNameConstants.ADD_COURSE_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class AddCourseActionTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        doNothing().when(adminService).addCourse(isA(CourseDTO.class));
        String path = new AddCourseAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ADD_COURSE_ACTION), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        doThrow(ValidateException.class).when(adminService).addCourse(isA(CourseDTO.class));
        String path = new AddCourseAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(ADD_COURSE_ACTION), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            when(req.getMethod()).thenReturn(GET);
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(appContext.getGeneralService()).thenReturn(generalService);
            when(generalService.getCategories(anyString())).thenReturn(testServiceUtil.getCategoryDTOS());
            when(generalService.getTeachers(anyString())).thenReturn(testServiceUtil.getTeacherDTOS());
            String path = new AddCourseAction(appContext).execute(req, resp);
            assertEquals(ADD_COURSE_PAGE, path);
        }
    }

    private void setUpReturn() throws ValidateException, ServiceException {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(TITLE)).thenReturn(TITLE);
        when(req.getParameter(DURATION)).thenReturn("1");
        when(req.getParameter(START_DATE)).thenReturn("2022-01-02");
        when(req.getParameter(STATUS)).thenReturn("COMING_SOON");
        when(req.getParameter(CATEGORY_ID)).thenReturn("1");
        when(req.getParameter(TEACHER_ID)).thenReturn("1");
        when(req.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION);
        when(adminService.getCategory(1)).thenReturn(testServiceUtil.getCategoryDTO());
        when(adminService.getTeacher(1)).thenReturn(testServiceUtil.getTeacherDTO());
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
    }
}