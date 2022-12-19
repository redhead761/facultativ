package com.epam.facultative.actions.impl.student;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class EnrollAction implements Action {
    private final StudentService studentService;

    public EnrollAction() {
        studentService = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        try {
            studentService.enroll(courseId, user.getId());
        } catch (ServiceException e) {
            req.getSession().setAttribute("message", "You already on course");
            return SHOW_STUDENT_CABINET_ACTION;
        }
        req.getSession().setAttribute("message", "Successful");
        return SHOW_STUDENT_CABINET_ACTION;
    }
}
