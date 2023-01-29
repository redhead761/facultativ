package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.constants.ActionNameConstants.SHOW_JOURNAL_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TeacherService teacherService = mock(TeacherService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executeSuccessful() throws ServiceException {
        when(req.getParameter(STUDENT_ID)).thenReturn("1");
        when(req.getParameter(GRADE)).thenReturn("1");
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
        when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
        when(req.getSession()).thenReturn(httpSession);
        when(appContext.getTeacherService()).thenReturn(teacherService);
        doNothing().when(teacherService).grading(anyInt(), anyInt(), anyInt());
        String path = new GradeAction(appContext).execute(req, resp);
        assertEquals(getGetAction(SHOW_JOURNAL_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1", COURSE_ID, "1"), path);
    }
}