package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.utils.pdf_creator.PdfCreator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DownloadCoursesAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(DownloadCoursesAction.class);
    private final GeneralService generalService;

    public DownloadCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        List<CourseDTO> courses = generalService.getAllCourses(0, Integer.MAX_VALUE);
        String locale = (String) req.getSession().getAttribute("language");
        PdfCreator pdfCreator = new PdfCreator();
        ByteArrayOutputStream usersPdf = pdfCreator.createCoursesPdf(courses, locale);
        setResponse(resp, usersPdf);
        return "index.jsp";
    }

    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"users.pdf\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
