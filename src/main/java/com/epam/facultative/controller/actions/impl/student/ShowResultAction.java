package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowResultAction implements Action {
    private final StudentService studentService;

    public ShowResultAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        int studentId = student.getId();
        int grade = studentService.getGrade(courseId, studentId);
        req.setAttribute(COURSE_ID, courseId);
        req.setAttribute(GRADE, grade);
        return RESULT_PAGE;
    }
}
