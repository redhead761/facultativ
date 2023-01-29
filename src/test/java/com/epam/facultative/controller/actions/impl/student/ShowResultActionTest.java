package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.AttributeConstants.COURSE_ID;
import static com.epam.facultative.controller.constants.AttributeConstants.USER;
import static com.epam.facultative.controller.constants.PageNameConstants.RESULT_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class ShowResultActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final StudentService studentService = mock(StudentService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executeSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getParameter(COURSE_ID)).thenReturn("1");
            when(req.getSession()).thenReturn(httpSession);
            when(httpSession.getAttribute(USER)).thenReturn(testServiceUtil.getStudentDTO());
            when(appContext.getStudentService()).thenReturn(studentService);
            when(studentService.getGrade(anyInt(), anyInt())).thenReturn(1);
            String path = new ShowResultAction(appContext).execute(req, resp);
            assertEquals(RESULT_PAGE, path);
        }
    }
}