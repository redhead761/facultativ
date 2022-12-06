package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;

public class UpdateCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        int id = Integer.parseInt(req.getParameter("category_id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Category category = new Category(id, title, description);
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.updateCategory(category);
            req.setAttribute("message", "Successful");
            path = MANAGE_CATEGORIES_ACTION;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        } catch (ValidateException e) {
            path = MANAGE_CATEGORIES_ACTION;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
