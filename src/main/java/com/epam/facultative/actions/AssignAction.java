package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class AssignAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.assigned(courseId, teacherId);
            req.setAttribute("message","Successful");
            path = MANAGE_COURSES_ACTION;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
