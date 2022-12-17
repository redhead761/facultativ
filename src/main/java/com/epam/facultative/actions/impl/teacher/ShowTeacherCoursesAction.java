package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class ShowTeacherCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String path;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        try {
            req.setAttribute("courses", teacherService.getTeacherCourses(user.getId(), 1, 5));
            path = TEACHER_COURSES_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
