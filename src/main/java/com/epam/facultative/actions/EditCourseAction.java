package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class EditCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String courseId = req.getParameter("course_id");
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            CourseDTO course = adminService.getCourse(Integer.parseInt(courseId));
            req.setAttribute("course", course);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "course_form.jsp";
    }
}

