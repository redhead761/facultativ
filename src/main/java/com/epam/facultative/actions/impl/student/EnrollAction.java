package com.epam.facultative.actions.impl.student;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class EnrollAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        StudentService studentService = ServiceFactory.getInstance().getStudentService();
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        req.getSession().setAttribute("courses", generalService.getAllCourses(0,3));
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        studentService.enroll(courseId, user.getId());
        req.getSession().setAttribute("message", "Successful");
        return STUDENT_PAGE;
    }
}
