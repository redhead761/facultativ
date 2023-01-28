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
import static com.epam.facultative.controller.constants.ActionNameConstants.MANAGE_CATEGORIES_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteCategoryActionTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executeSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        doNothing().when(adminService).deleteCategory(anyInt());
        String path = new DeleteCategoryAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(MANAGE_CATEGORIES_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1"), path);
    }

    @Test
    void executeWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        doThrow(ValidateException.class).when(adminService).deleteCategory(anyInt());
        String path = new DeleteCategoryAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(MANAGE_CATEGORIES_ACTION, CURRENT_PAGE, "1", RECORDS_PER_PAGE, "1"), path);
    }

    private void setUpReturn() {
        when(req.getParameter(CATEGORY_ID)).thenReturn("1");
        when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
        when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
    }


}