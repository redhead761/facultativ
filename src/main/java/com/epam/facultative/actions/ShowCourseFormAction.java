package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ShowCourseFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        String courseId = req.getParameter("course_id");
        try {
            List<Category> categories = adminService.getAllCategories();
            req.setAttribute("categories", categories);
            req.setAttribute("course_id", courseId);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "course_form.jsp";
    }
}
