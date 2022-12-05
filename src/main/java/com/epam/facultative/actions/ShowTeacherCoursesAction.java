package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class ShowTeacherCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        int userId = Integer.parseInt(req.getParameter("user_id"));
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        try {
            req.setAttribute("courses", teacherService.getTeacherCourses(userId));
            path = TEACHER_COURSES_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
