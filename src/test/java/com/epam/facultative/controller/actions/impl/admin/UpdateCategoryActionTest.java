package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.UPDATE_CATEGORY_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.DESCRIPTION;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_CATEGORY_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UpdateCategoryActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AdminService adminService = mock(AdminService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturnPost();
        doNothing().when(adminService).updateCategory(isA(CategoryDTO.class));
        String path = new UpdateCategoryAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, CHANGES_SAVED);
        assertEquals(getGetAction(UPDATE_CATEGORY_ACTION, CATEGORY_ID, "1"), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturnPost();
        doThrow(ValidateException.class).when(adminService).updateCategory(isA(CategoryDTO.class));
        String path = new UpdateCategoryAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, CHANGES_SAVED);
        assertEquals(getGetAction(UPDATE_CATEGORY_ACTION, CATEGORY_ID, "1"), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException, ValidateException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            setUpReturnGet();
            when(adminService.getCategory(anyInt())).thenReturn(testServiceUtil.getCategoryDTO());
            String path = new UpdateCategoryAction(appContext).execute(req, resp);
            assertEquals(EDIT_CATEGORY_PAGE, path);
        }
    }

    @Test
    void executeGetWithValidateException() throws ServiceException, ValidateException {
        try (MockedStatic<ActionUtils> actionUtil = mockStatic(ActionUtils.class)) {
            actionUtil.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            setUpReturnGet();
            when(adminService.getCategory(anyInt())).thenThrow(ValidateException.class);
            String path = new UpdateCategoryAction(appContext).execute(req, resp);
            assertEquals(EDIT_CATEGORY_PAGE, path);
        }
    }

    private void setUpReturnPost() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(TITLE)).thenReturn(TITLE);
        when(req.getParameter(CATEGORY_ID)).thenReturn("1");
        when(req.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION);
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getSession()).thenReturn(httpSession);
    }

    private void setUpReturnGet() {
        when(req.getMethod()).thenReturn(GET);
        when(appContext.getAdminService()).thenReturn(adminService);
        when(req.getParameter(CATEGORY_ID)).thenReturn("1");
    }

}