package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class DeleteCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        String id = req.getParameter("course_id");
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            adminService.deleteCourse(Integer.parseInt(id));
            req.setAttribute("courses", generalService.getAllCourses());
            req.setAttribute("teachers", generalService.getAllTeachers());
            req.setAttribute("categories", generalService.getAllCategories());
            req.setAttribute("message", "Successful");
            path = ADMIN_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
