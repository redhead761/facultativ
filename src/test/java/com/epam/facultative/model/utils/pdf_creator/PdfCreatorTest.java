package com.epam.facultative.model.utils.pdf_creator;

import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PdfCreatorTest {
    private final ServletContext servletContext = mock(ServletContext.class);
    private final String FONT = "file:./../fonts/arial.ttf";
    private final String EN = "en";
    private final String UA = "uk_Ua";
    private static List<CourseDTO> courses;
    private static PdfCreator pdfCreator;
    private static StudentDTO studentDTO;
    private static Course course;

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        courses = testServiceUtil.getTCourseDTOS().getValue();
        pdfCreator = new PdfCreator();
        studentDTO = testServiceUtil.getStudentDTO();
        course = testServiceUtil.getCourse();
    }

    @Test
    void testCreateCertificateForDownload() {
        assertDoesNotThrow(() -> pdfCreator.createCertificateForDownload(studentDTO, course, 1));
    }

    @Test
    void testCreateCertificateForSend() {
        assertDoesNotThrow(() -> pdfCreator.createCertificateForSend(studentDTO, course, 1));
    }

    @Test
    void testUserPdfEn() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenReturn(new URL(FONT));
        assertDoesNotThrow(() -> pdfCreator.createCoursesPdf(courses, EN));
    }

    @Test
    void testUserPdfUa() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenReturn(new URL(FONT));
        assertDoesNotThrow(() -> pdfCreator.createCoursesPdf(courses, UA));
    }

    @Test
    void testEventPdfEn() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenReturn(new URL(FONT));
        assertDoesNotThrow(() -> pdfCreator.createCoursesPdf(courses, EN));
    }

    @Test
    void testEventPdfUa() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenReturn(new URL(FONT));
        assertDoesNotThrow(() -> pdfCreator.createCoursesPdf(courses, UA));
    }

    @Test
    void testEventPdfNoFont() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenReturn(new URL("file:./../fonts/wrong.ttf"));
        assertDoesNotThrow(() -> pdfCreator.createCoursesPdf(courses, UA));
    }

}