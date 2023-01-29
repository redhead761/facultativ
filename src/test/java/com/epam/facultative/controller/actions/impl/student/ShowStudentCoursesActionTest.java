package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.TYPE;
import static com.epam.facultative.controller.constants.AttributeConstants.USER;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowStudentCoursesActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final StudentService studentService = mock(StudentService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private static StudentDTO studentDTO;
    private static Map.Entry<Integer, List<CourseDTO>> courses;

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        studentDTO = testServiceUtil.getStudentDTO();
        courses = testServiceUtil.getTCourseDTOS();
    }

    @Test
    void executeAllSuccessful() throws ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("all");
        String path = new ShowStudentCoursesAction(appContext).execute(req, resp);
        assertEquals(STUDENT_COURSES_PAGE, path);
    }

    @Test
    void executeComingSoonSuccessful() throws ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("coming_soon");
        String path = new ShowStudentCoursesAction(appContext).execute(req, resp);
        assertEquals(COMING_SOON_COURSES_PAGE, path);
    }

    @Test
    void executeCompletedSuccessful() throws ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("completed");
        String path = new ShowStudentCoursesAction(appContext).execute(req, resp);
        assertEquals(COMPLETED_COURSES_PAGE, path);
    }

    @Test
    void executeInProcessSuccessful() throws ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("in_progress");
        String path = new ShowStudentCoursesAction(appContext).execute(req, resp);
        assertEquals(IN_PROGRESS_COURSES_PAGE, path);
    }

    void setUpReturn() throws ServiceException {
        when(req.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(USER)).thenReturn(studentDTO);
        when(appContext.getStudentService()).thenReturn(studentService);
        when(studentService.getCoursesByJournal(anyString())).thenReturn(courses);
    }
}