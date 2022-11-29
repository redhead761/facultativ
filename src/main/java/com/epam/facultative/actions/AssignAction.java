package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class AssignAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.assigned(courseId,teacherId);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "assign_page.jsp";
    }
}
