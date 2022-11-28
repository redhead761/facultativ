package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AdminCategoriesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            List<Category> categories = adminService.getAllCategories();
            req.setAttribute("categories", categories);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_categories.jsp";
    }
}
