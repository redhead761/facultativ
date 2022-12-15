package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.*;
import com.epam.facultative.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;

public class AddCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path;
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        try {
            adminService.addCategory(category);
            req.setAttribute("message", "Successful");
            path = CATEGORY_FORM_PAGE;
        } catch (ValidateException e) {
            req.setAttribute("title", title);
            req.setAttribute("description", description);
            path = CATEGORY_FORM_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
