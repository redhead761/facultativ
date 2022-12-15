package com.epam.facultative.actions.impl.student;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class ShowCompletedCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        StudentService studentService = ServiceFactory.getInstance().getStudentService();
        req.setAttribute("courses", studentService.getCoursesCompleted(user.getId()));
        return COMPLETED_COURSES_PAGE;
    }
}
