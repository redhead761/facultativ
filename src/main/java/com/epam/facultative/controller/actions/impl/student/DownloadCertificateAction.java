package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.facultative.controller.AttributeConstants.MESSAGE;
import static com.epam.facultative.controller.actions.PageNameConstants.INDEX_PAGE;

public class DownloadCertificateAction implements Action {
    private final StudentService studentService;

    public DownloadCertificateAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        StudentDTO student = (StudentDTO) req.getSession().getAttribute("user");
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int grade = Integer.parseInt(req.getParameter("grade"));
        ByteArrayOutputStream certificate;
        try {
            certificate = studentService.downloadCertificate(student, courseId, grade);
            setResponse(resp, certificate);
        } catch (ValidateException e) {
            req.setAttribute(MESSAGE, e.getMessage());
        }
        return INDEX_PAGE;
    }

    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"certificate.pdf\"");
        OutputStream outputStream = response.getOutputStream();
        output.writeTo(outputStream);
        outputStream.flush();
    }
}
