package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowCategoryFormAction implements Action {
    private final AdminService adminService;

    public ShowCategoryFormAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int categoryId = Integer.parseInt(req.getParameter("category_id"));
        CategoryDTO category = adminService.getCategory(categoryId);
        req.setAttribute("title", category.getTitle());
        req.setAttribute("description", category.getDescription());
        req.setAttribute("category_id", categoryId);
        return EDIT_CATEGORY_PAGE;
    }
}

