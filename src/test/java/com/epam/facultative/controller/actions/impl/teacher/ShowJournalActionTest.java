package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.TeacherService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.JOURNAL_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class ShowJournalActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TeacherService teacherService = mock(TeacherService.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executeSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getParameter(COURSE_ID)).thenReturn("1");
            when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
            when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
            when(appContext.getTeacherService()).thenReturn(teacherService);
            when(teacherService.getStudentsByCourse(anyString())).thenReturn(testServiceUtil.getStudentDTOS());
            actionUtils.when(() -> setUpPaginate(isA(HttpServletRequest.class), anyInt())).thenAnswer(invocation -> null);
            String path = new ShowJournalAction(appContext).execute(req, resp);
            assertEquals(JOURNAL_PAGE, path);
        }
    }
}