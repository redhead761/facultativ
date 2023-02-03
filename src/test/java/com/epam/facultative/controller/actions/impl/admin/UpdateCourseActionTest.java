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
import static com.epam.facultative.controller.constants.ActionNameConstants.UPDATE_COURSE_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.DESCRIPTION;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_COURSE_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UpdateCourseActionTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();


    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturnPost();
        doNothing().when(adminService).updateCourse(isA(CourseDTO.class));
        String path = new UpdateCourseAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, CHANGES_SAVED);
        assertEquals(getGetAction(UPDATE_COURSE_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturnPost();
        doThrow(ValidateException.class).when(adminService).updateCourse(isA(CourseDTO.class));
        String path = new UpdateCourseAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, CHANGES_SAVED);
        assertEquals(getGetAction(UPDATE_COURSE_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException, ValidateException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            setUpReturnGet();
            when(adminService.getCourse(anyInt())).thenReturn(testServiceUtil.getCourseDTO());
            String path = new UpdateCourseAction(appContext).execute(req, resp);
            assertEquals(EDIT_COURSE_PAGE, path);
        }
    }

    @Test
    void executeGetWithValidateException() throws ServiceException, ValidateException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            setUpReturnGet();
            when(adminService.getCourse(anyInt())).thenThrow(ValidateException.class);
            String path = new UpdateCourseAction(appContext).execute(req, resp);
            assertEquals(EDIT_COURSE_PAGE, path);
        }
    }

    private void setUpReturnPost() throws ValidateException, ServiceException {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(TITLE)).thenReturn(TITLE);
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(req.getParameter(DURATION)).thenReturn("1");
        when(req.getParameter(START_DATE)).thenReturn("2022-01-02");
        when(req.getParameter(STATUS)).thenReturn("COMING_SOON");
        when(req.getParameter(CATEGORY_ID)).thenReturn("1");
        when(req.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION);
        when(req.getParameter(EMAIL_SEND)).thenReturn("true");
        when(adminService.getCategory(1)).thenReturn(testServiceUtil.getCategoryDTO());
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
        when(req.getParameter(TEACHER_ID)).thenReturn("1");
        when(adminService.getTeacher(anyInt())).thenReturn(testServiceUtil.getTeacherDTO());
        doNothing().when(adminService).courseLaunchNewsLetter(isA(CourseDTO.class), isA(AppContext.class));
    }

    private void setUpReturnGet() throws ServiceException {
        when(req.getMethod()).thenReturn(GET);
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(appContext.getGeneralService()).thenReturn(generalService);
        when(generalService.getCategories(anyString())).thenReturn(testServiceUtil.getCategoryDTOS());
        when(generalService.getTeachers(anyString())).thenReturn(testServiceUtil.getTeacherDTOS());
        when(appContext.getAdminService()).thenReturn(adminService);
    }
}