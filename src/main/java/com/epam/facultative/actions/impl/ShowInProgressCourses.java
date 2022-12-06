package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;


public class ShowInProgressCourses implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        StudentService studentService = ServiceFactory.getInstance().getStudentService();
        try {
            req.setAttribute("courses", studentService.getCoursesInProgress(user.getId()));
            path = IN_PROGRESS_COURSES_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
