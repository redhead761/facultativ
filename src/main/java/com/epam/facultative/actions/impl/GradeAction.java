package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;

public class GradeAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        int grade = Integer.parseInt(req.getParameter("grade"));
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        try {
            teacherService.grading(courseId, studentId, grade);
            req.setAttribute("message", "Successful");
            path = SHOW_TEACHER_COURSES_ACTION;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            throw new RuntimeException(e);
        }
        return path;
    }
}
