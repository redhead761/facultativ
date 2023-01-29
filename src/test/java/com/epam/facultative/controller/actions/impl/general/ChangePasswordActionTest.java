package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.CHANGE_PASSWORD_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ChangePasswordActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    void executePostSuccessful() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(NEW_PASSWORD);
        doNothing().when(generalService).changePassword(anyString(), anyString(), anyInt());
        String path = new ChangePasswordAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(CHANGE_PASSWORD_ACTION), path);
    }

    @Test
    void executePostWithWrongRepeatPassword() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(REPEAT_PASSWORD);
        doNothing().when(generalService).changePassword(anyString(), anyString(), anyInt());
        String path = new ChangePasswordAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(CHANGE_PASSWORD_ACTION), path);
    }

    @Test
    void executePostWithValidateException() throws ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(REPEAT_PASSWORD)).thenReturn(NEW_PASSWORD);
        doThrow(ValidateException.class).when(generalService).changePassword(anyString(), anyString(), anyInt());
        String path = new ChangePasswordAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(CHANGE_PASSWORD_ACTION), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            String path = new ChangePasswordAction(appContext).execute(req, resp);
            assertEquals(CHANGE_PASSWORD_PAGE, path);
        }
    }

    private void setUpReturn() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(OLD_PASSWORD)).thenReturn(OLD_PASSWORD);
        when(req.getParameter(NEW_PASSWORD)).thenReturn(NEW_PASSWORD);
        when(req.getParameter(USER_ID)).thenReturn("1");
        when(appContext.getGeneralService()).thenReturn(generalService);
        when(req.getSession()).thenReturn(httpSession);
    }

}