package com.epam.facultative.controller.filters;

import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.dto.UserDTO;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;

import static com.epam.facultative.controller.constants.AttributeConstants.USER;
import static org.mockito.Mockito.*;

class AuthFilterTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final FilterChain filterChain = mock(FilterChain.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private static UserDTO adminDTO;
    private static TeacherDTO teacherDTO;
    private static StudentDTO studentDTO;

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        adminDTO = testServiceUtil.getAdminDTO();
        teacherDTO = testServiceUtil.getTeacherDTO();
        studentDTO = testServiceUtil.getStudentDTO();
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/noLoggedUserActions.csv"})
    void testNoLoggedAccessActions(String action) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(null);
        testActions(action);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/noLoggedUserPages.csv"})
    void testNoLoggedAccessPages(String page) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(null);
        testPages(page);
    }

    @Test
    void testNoLoggedDenied() throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(null);
        testDenied();
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/adminActions.csv"})
    void testAdminAccessActions(String action) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(adminDTO);
        testActions(action);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/adminPages.csv"})
    void testAdminAccessPages(String page) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(adminDTO);
        testPages(page);
    }

    @Test
    void tesAdminDenied() throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(adminDTO);
        testDenied();
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/teacherActions.csv"})
    void testTeacherAccessActions(String action) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(teacherDTO);
        testActions(action);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/teacherPages.csv"})
    void testTeacherAccessPages(String page) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(teacherDTO);
        testPages(page);
    }

    @Test
    void tesTeacherDenied() throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(teacherDTO);
        testDenied();
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/studentActions.csv"})
    void testStudentAccessActions(String action) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(studentDTO);
        testActions(action);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/studentPages.csv"})
    void testStudentAccessPages(String page) throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(studentDTO);
        testPages(page);
    }

    @Test
    void tesStudentDenied() throws ServletException, IOException {
        when(httpSession.getAttribute(USER)).thenReturn(studentDTO);
        testDenied();
    }

    private void setUpReturn() throws ServletException, IOException {
        when(req.getSession()).thenReturn(httpSession);
        doNothing().when(filterChain).doFilter(req, resp);
    }

    private void testActions(String action) throws ServletException, IOException {
        setUpReturn();
        when(req.getParameter("action")).thenReturn(action);
        when(req.getServletPath()).thenReturn("abc");
        AuthFilter authFilter = new AuthFilter();
        authFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    private void testPages(String page) throws ServletException, IOException {
        setUpReturn();
        when(req.getParameter("action")).thenReturn("action");
        when(req.getServletPath()).thenReturn(page);
        AuthFilter authFilter = new AuthFilter();
        authFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }

    private void testDenied() throws ServletException, IOException {
        setUpReturn();
        when(req.getParameter("action")).thenReturn("action");
        when(req.getServletPath()).thenReturn("abc");
        AuthFilter authFilter = new AuthFilter();
        authFilter.doFilter(req, resp, filterChain);
        verify(filterChain, times(0)).doFilter(req, resp);
    }

}