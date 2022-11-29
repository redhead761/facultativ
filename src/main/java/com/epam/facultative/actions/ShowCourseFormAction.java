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

        try {

            List<Category> categories = adminService.getAllCategories();
            Status[] statuses = Status.values();
            req.setAttribute("categories", categories);
            req.setAttribute("statuses", statuses);
            if (req.getParameter("course_id") != null) {
                int courseId = Integer.parseInt(req.getParameter("course_id"));
                req.setAttribute("course", adminService.getCourse(courseId));
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "course_form.jsp";
    }
}
