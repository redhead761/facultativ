package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class GradeAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String path;
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        int grade = Integer.parseInt(req.getParameter("grade"));
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        teacherService.grading(courseId, studentId, grade);
        req.setAttribute("message", "Successful");
        path = SHOW_TEACHER_COURSES_ACTION;
        return path;
    }
}
