package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowCategoryFormAction implements Action {
    private final AdminService adminService;

    public ShowCategoryFormAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int categoryId = Integer.parseInt(req.getParameter(CATEGORY_ID));
        CategoryDTO category = null;
        try {
            category = adminService.getCategory(categoryId);
        } catch (ValidateException e) {
            req.setAttribute(ERROR, e.getMessage());
        }
        req.getSession().setAttribute(CATEGORY, category);
        return EDIT_CATEGORY_PAGE;
    }
}

