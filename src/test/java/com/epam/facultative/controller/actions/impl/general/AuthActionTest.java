package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import com.epam.facultative.model.utils.recaptcha.Recaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.ActionNameConstants.AUTH_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class AuthActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final Recaptcha recaptcha = mock(Recaptcha.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executePostSuccessfulAdmin() throws ValidateException, ServiceException {
        setUpReturn();
        when(recaptcha.verify(anyString())).thenReturn(true);
        when(generalService.authorization(anyString(), anyString())).thenReturn(testServiceUtil.getAdminDTO());
        String path = new AuthAction(appContext).execute(req, resp);
        assertEquals(ADMIN_PROFILE_PAGE, path);
    }

    @Test
    void executePostSuccessfulTeacher() throws ValidateException, ServiceException {
        setUpReturn();
        when(recaptcha.verify(anyString())).thenReturn(true);
        when(generalService.authorization(anyString(), anyString())).thenReturn(testServiceUtil.getTeacherDTO());
        String path = new AuthAction(appContext).execute(req, resp);
        assertEquals(TEACHER_PROFILE_PAGE, path);
    }

    @Test
    void executePostSuccessfulStudent() throws ValidateException, ServiceException {
        setUpReturn();
        when(recaptcha.verify(anyString())).thenReturn(true);
        when(generalService.authorization(anyString(), anyString())).thenReturn(testServiceUtil.getStudentDTO());
        String path = new AuthAction(appContext).execute(req, resp);
        assertEquals(STUDENT_PROFILE_PAGE, path);
    }

    @Test
    void executePostWithValidateException1() throws ValidateException, ServiceException {
        setUpReturn();
        when(recaptcha.verify(anyString())).thenReturn(false);
        when(generalService.authorization(anyString(), anyString())).thenReturn(testServiceUtil.getAdminDTO());
        String path = new AuthAction(appContext).execute(req, resp);
        assertEquals(getGetAction(AUTH_ACTION), path);
    }

    @Test
    void executePostWithValidateException2() throws ValidateException, ServiceException {
        setUpReturn();
        when(recaptcha.verify(anyString())).thenReturn(true);
        when(generalService.authorization(anyString(), anyString())).thenThrow(ValidateException.class);
        String path = new AuthAction(appContext).execute(req, resp);
        assertEquals(getGetAction(AUTH_ACTION), path);
    }

    @Test
    void executeGetSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            String path = new AuthAction(appContext).execute(req, resp);
            assertEquals(AUTH_PAGE, path);
        }
    }


    private void setUpReturn() {
        when(req.getMethod()).thenReturn(POST);
        when(req.getParameter(LOGIN)).thenReturn(LOGIN);
        when(req.getParameter(PASSWORD)).thenReturn(PASSWORD);
        when(req.getParameter(RECAPTCHA)).thenReturn(RECAPTCHA);
        when(req.getSession()).thenReturn(httpSession);
        when(appContext.getRecaptcha()).thenReturn(recaptcha);
        when(appContext.getGeneralService()).thenReturn(generalService);
    }

}