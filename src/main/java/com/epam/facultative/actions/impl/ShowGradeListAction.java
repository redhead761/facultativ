package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.epam.facultative.actions.impl.Constants.*;

public class ShowGradeListAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        try {
            List<UserDTO> students = teacherService.getStudentsByCourse(courseId);
            req.setAttribute("students", students);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return GRADE_PAGE;
    }
}
