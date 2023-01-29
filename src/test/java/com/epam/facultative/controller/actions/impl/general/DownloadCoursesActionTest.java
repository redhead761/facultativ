package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.facultative.controller.constants.AttributeConstants.LANGUAGE;
import static com.epam.facultative.controller.constants.PageNameConstants.INDEX_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DownloadCoursesActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);

    @Test
    void executeSuccessful() throws ServiceException, IOException {
        when(req.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(LANGUAGE)).thenReturn(LANGUAGE);
        when(resp.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        when(appContext.getGeneralService()).thenReturn(generalService);
        when(generalService.downloadAllCoursesInPdf(anyString(), isA(AppContext.class))).thenReturn(byteArrayOutputStream);
        String path = new DownloadCoursesAction(appContext).execute(req, resp);
        assertEquals(INDEX_PAGE, path);
    }
}