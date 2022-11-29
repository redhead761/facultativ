package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class AuthAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            UserDTO user = generalService.authorization(login, password);
            List<CourseDTO> courses = generalService.sortCoursesByAlphabet();
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("courses", courses);
            switch (user.getRole()) {
                case ADMIN -> path = ADMIN_PAGE;
                case TEACHER -> {
                    TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
                    List<CourseDTO> coursesTeacher = teacherService.getTeacherCourses(user.getId());
                    req.setAttribute("coursesTeacher" , coursesTeacher);

                    path = TEACHER_PAGE;
                }
                case STUDENT -> path = STUDENT_PAGE;
            }
        } catch (ValidateException e) {
            path = AUTH_PAGE;
            req.setAttribute("error", e.getMessage());
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
