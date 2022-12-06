package com.epam.facultative.actions;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class ShowComingSoonCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        StudentService studentService = ServiceFactory.getInstance().getStudentService();
        try {
            req.setAttribute("courses", studentService.getCoursesComingSoon(user.getId()));
            path = COMING_SOON_COURSES_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
