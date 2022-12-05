package com.epam.facultative.actions;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class EnrollAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        StudentService studentService = ServiceFactory.getInstance().getStudentService();
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            req.setAttribute("courses", generalService.getAllCourses());
            req.setAttribute("categories", generalService.getAllCategories());
            req.setAttribute("teachers", generalService.getAllTeachers());
            studentService.enroll(courseId,user.getId());
            req.setAttribute("message", "Successful");
        } catch (ServiceException e) {
            req.setAttribute("message", "You are studying in this course");
        }
        return STUDENT_PAGE;
    }
}
