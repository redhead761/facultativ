package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GradeAction implements Action {
    private final TeacherService teacherService;

    public GradeAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        int grade = Integer.parseInt(req.getParameter("grade"));
        teacherService.grading(courseId, studentId, grade);
        req.setAttribute("message", "Successful");
        return "controller?action=show_journal&page=" + req.getParameter("page") + "&records_per_page=" + req.getParameter("records_per_page") + "&course_id=" + req.getParameter("course_id");
    }
}
