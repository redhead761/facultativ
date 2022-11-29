package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ShowAssignPageAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            CourseDTO course = adminService.getCourse(courseId);
            List<UserDTO> teachers = adminService.getAllTeachers();
            req.setAttribute("course", course);
            req.setAttribute("teachers", teachers);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "assign_page.jsp";
    }
}
