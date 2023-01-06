package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.entities.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;

public class UpdateCategoryAction implements Action {
    private final AdminService adminService;

    public UpdateCategoryAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        Category category = getCategoryFromParameter(req);
        try {
            adminService.updateCategory(category);
            req.getSession().setAttribute("message", "Changes saved");
        } catch (ValidateException e) {
            req.setAttribute("title", category.getTitle());
            req.setAttribute("description", category.getDescription());
            req.setAttribute("category_id", req.getParameter("category_id"));
            req.getSession().setAttribute("message", e.getMessage());
            req.getRequestDispatcher(EDIT_CATEGORY_PAGE).forward(req, resp);
        }
        return EDIT_CATEGORY_PAGE;
    }

    private Category getCategoryFromParameter(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("category_id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        return Category.builder()
                .id(id)
                .title(title)
                .description(description)
                .build();
    }
}
