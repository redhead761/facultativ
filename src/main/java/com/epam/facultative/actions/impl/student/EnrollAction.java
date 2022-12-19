package com.epam.facultative.actions.impl.student;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class EnrollAction implements Action {
    private final StudentService studentService;
    private final GeneralService generalService;

    public EnrollAction() {
        studentService = ServiceFactory.getInstance().getStudentService();
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        int currentPage = (int) req.getSession().getAttribute("currentPage");
        int recordsPerPage = 5;
        try {
            studentService.enroll(courseId, user.getId());
            ActionUtils.setUpPaginationForCourses(req, generalService, currentPage, recordsPerPage);
        } catch (ServiceException e) {
            req.getSession().setAttribute("message", "You already on course");
            return STUDENT_PAGE;
        }
        req.getSession().setAttribute("message", "Successful");
        return STUDENT_PAGE;
    }
}
