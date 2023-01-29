package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
import static com.epam.facultative.controller.constants.ActionNameConstants.SHOW_RESULT_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CertificateActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final StudentService studentService = mock(StudentService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);

    @Test
    void executeSuccessfulDownload() throws IOException, ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("download");
        when(resp.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        when(studentService.downloadCertificate(isA(StudentDTO.class), anyInt(), anyInt(), isA(AppContext.class))).thenReturn(byteArrayOutputStream);
        String path = new CertificateAction(appContext).execute(req, resp);
        verify(req, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(SHOW_RESULT_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executeSuccessfulSend() throws IOException, ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("send");
        doNothing().when(studentService).sendCertificate(isA(StudentDTO.class), anyInt(), anyInt(), isA(AppContext.class));
        String path = new CertificateAction(appContext).execute(req, resp);
        verify(req, times(1)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(SHOW_RESULT_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executeWithValidateExceptionDownload() throws IOException, ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("download");
        when(resp.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        when(studentService.downloadCertificate(isA(StudentDTO.class), anyInt(), anyInt(), isA(AppContext.class))).thenThrow(ValidateException.class);
        String path = new CertificateAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(SHOW_RESULT_ACTION, COURSE_ID, "1"), path);
    }

    @Test
    void executeWitchValidateExceptionSend() throws IOException, ValidateException, ServiceException {
        setUpReturn();
        when(req.getParameter(TYPE)).thenReturn("send");
        doThrow(ValidateException.class).when(studentService).sendCertificate(isA(StudentDTO.class), anyInt(), anyInt(), isA(AppContext.class));
        String path = new CertificateAction(appContext).execute(req, resp);
        verify(httpSession, times(0)).setAttribute(MESSAGE, SUCCESSFUL);
        assertEquals(getGetAction(SHOW_RESULT_ACTION, COURSE_ID, "1"), path);
    }

    private void setUpReturn() {
        when(req.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(USER)).thenReturn(testServiceUtil.getStudentDTO());
        when(req.getParameter(COURSE_ID)).thenReturn("1");
        when(req.getParameter(GRADE)).thenReturn("1");
        when(appContext.getStudentService()).thenReturn(studentService);
    }

}