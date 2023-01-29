package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.constants.ActionNameConstants.MY_CABINET_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AddAvatarActionTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final Part part = mock(Part.class);
    private final InputStream inputStream = mock(InputStream.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executeSuccessful() throws ServletException, IOException, ValidateException, ServiceException {
        setUpReturn();
        when(generalService.addAvatar(anyInt(), isA(InputStream.class))).thenReturn(testServiceUtil.getAdminDTO());
        String path = new AddAvatarAction(appContext).execute(req, resp);
        verify(httpSession, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(MY_CABINET_ACTION), path);
    }

    @Test
    void executeWithValidateException() throws ServletException, IOException, ValidateException, ServiceException {
        setUpReturn();
        when(generalService.addAvatar(anyInt(), isA(InputStream.class))).thenThrow(ValidateException.class);
        String path = new AddAvatarAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(MY_CABINET_ACTION), path);
    }

    private void setUpReturn() throws ServletException, IOException {
        when(req.getParameter(USER_ID)).thenReturn("1");
        when(req.getPart(AVATAR)).thenReturn(part);
        when(part.getInputStream()).thenReturn(inputStream);
        when(appContext.getGeneralService()).thenReturn(generalService);
        when(req.getSession()).thenReturn(httpSession);
    }
}