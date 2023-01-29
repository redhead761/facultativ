package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.AttributeConstants.CURRENT_PAGE;
import static com.epam.facultative.controller.constants.AttributeConstants.RECORDS_PER_PAGE;
import static com.epam.facultative.controller.constants.PageNameConstants.MANAGE_STUDENTS_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ManageStudentsActionTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executePostSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
            when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
            when(appContext.getAdminService()).thenReturn(adminService);
            when(adminService.getStudents(anyString())).thenReturn(testServiceUtil.getStudentDTOS());
            actionUtils.when(() -> setUpPaginate(isA(HttpServletRequest.class), anyInt())).thenAnswer(invocation -> null);

            String path = new ManageStudentsAction(appContext).execute(req, resp);
            assertEquals(MANAGE_STUDENTS_PAGE, path);
        }
    }

}