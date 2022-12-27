package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import static com.epam.facultative.actions.PageNameConstants.*;

public class GradeAction implements Action {
    private final TeacherService teacherService;

    public GradeAction() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String path;
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        int grade = Integer.parseInt(req.getParameter("grade"));
        teacherService.grading(courseId, studentId, grade);
        req.setAttribute("message", "Successful");
        path = SHOW_TEACHER_COURSES_ACTION;
        return path;
    }
}
