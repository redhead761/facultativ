package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.*;
import com.epam.facultative.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;

public class UpdateCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path;
        int id = (int) req.getSession().getAttribute("category_id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Category category = new Category(id, title, description);
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.updateCategory(category);
//            req.setAttribute("message", "Successful");
            path = MANAGE_CATEGORIES_ACTION;
            req.getSession().removeAttribute("category_id");
        } catch (ValidateException e) {
            path = MANAGE_CATEGORIES_ACTION;
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(path).forward(req, resp);
        }
        return path;
    }
}
