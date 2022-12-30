package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.entities.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;

public class AddCategoryAction implements Action {

    private final AdminService adminService;

    public AddCategoryAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        ActionUtils.removeRedundantAttribute(req);
        Category category = getCategoryFromParameter(req);
        try {
            adminService.addCategory(category);
            req.getSession().setAttribute("message", "Successful");
        } catch (ValidateException e) {
            req.setAttribute("title", category.getTitle());
            req.setAttribute("description", category.getDescription());
            req.getSession().setAttribute("message", e.getMessage());
            req.getRequestDispatcher(ADD_CATEGORY_PAGE).forward(req, resp);
        }
        return ADD_CATEGORY_PAGE;
    }

    private Category getCategoryFromParameter(HttpServletRequest req) {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        return Category.builder()
                .title(title)
                .description(description)
                .build();
    }
}
