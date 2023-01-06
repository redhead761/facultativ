package com.epam.facultative.actions.impl.student;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowResultAction implements com.epam.facultative.actions.Action {
    private final StudentService studentService;

    public ShowResultAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        StudentDTO student = (StudentDTO) req.getSession().getAttribute("user");
        int studentId = student.getId();
        int grade = studentService.getGrade(courseId, studentId);
        req.setAttribute("grade", grade);
        return RESULT_PAGE;
    }
}
