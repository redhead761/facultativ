package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class UpdateCategoryAction implements Action {
    private final AdminService adminService;

    public UpdateCategoryAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Category category = getCategoryFromParameter(req);
        try {
            adminService.updateCategory(category);
            req.setAttribute("message", "Successful");
        } catch (ValidateException e) {
            req.setAttribute("title", category.getTitle());
            req.setAttribute("description", category.getDescription());
            req.setAttribute("message", e.getMessage());
            return CATEGORY_FORM_PAGE;
        }
        req.getSession().removeAttribute("category_id");
        return MANAGE_CATEGORIES_ACTION;
    }

    private Category getCategoryFromParameter(HttpServletRequest req) {
        int id = (int) req.getSession().getAttribute("category_id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        return new Category(id, title, description);
    }
}
