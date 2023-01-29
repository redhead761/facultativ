package com.epam.facultative.controller.actions;

import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActionUtilsTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void setAllCoursesSuccessful() throws ServiceException {
        when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
        when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
        when(req.getParameter(SELECT_BY_CATEGORY)).thenReturn("1");
        when(req.getParameter(SELECT_BY_TEACHER)).thenReturn("1");
        when(req.getParameter(SORT)).thenReturn("sort");
        when(req.getParameter(ORDER)).thenReturn("desc");
        when(generalService.getCourses(anyString())).thenReturn(testServiceUtil.getTCourseDTOS());
        when(generalService.getTeachers(anyString())).thenReturn(testServiceUtil.getTeacherDTOS());
        when(generalService.getCategories(anyString())).thenReturn(testServiceUtil.getCategoryDTOS());
        setAllCourses(req, generalService);
    }

    @Test
    void setAllTeachersSuccessful() throws ServiceException {
        when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
        when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
        when(generalService.getTeachers(anyString())).thenReturn(testServiceUtil.getTeacherDTOS());
        setAllTeachers(req, generalService);
    }

    @Test
    void getCorrectIntValueSuccessful() {
        int actual = getCorrectIntValue("2", 5);
        assertEquals(2, actual);
    }

    @Test
    void getCorrectIntValueWithLessZero() {
        int actual = getCorrectIntValue("-1", 5);
        assertEquals(5, actual);
    }

    @Test
    void getCorrectIntValueWithException() {
        int actual = getCorrectIntValue("ASD", 5);
        assertEquals(5, actual);
    }

    @Test
    void transferAttributeFromSessionToRequestSuccessful() {
        when(req.getSession()).thenReturn(httpSession);
        transferAttributeFromSessionToRequest(req, MESSAGE);
    }
}