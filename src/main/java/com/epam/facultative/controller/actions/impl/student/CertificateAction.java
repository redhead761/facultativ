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

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;

public class CertificateAction implements Action {
    private final StudentService studentService;

    public CertificateAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        int grade = Integer.parseInt(req.getParameter(GRADE));
        String type = req.getParameter(TYPE);
        try {
            switch (type) {
                case "download" -> {
                    ByteArrayOutputStream certificate = studentService.downloadCertificate(student, courseId, grade);
                    setResponse(resp, certificate);
                }
                case "send" -> studentService.sendCertificate(student, courseId, grade);
            }
        } catch (ValidateException e) {
            req.setAttribute(ERROR, e.getMessage());
        }
        req.setAttribute(MESSAGE, SUCCESSFUL);
        return getGetAction(SHOW_RESULT_ACTION, COURSE_ID, String.valueOf(courseId));
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
