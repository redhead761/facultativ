package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class DeleteCategoryAction implements Action {
    private final AdminService adminService;

    public DeleteCategoryAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int id = Integer.parseInt(req.getParameter(CATEGORY_ID));
        adminService.deleteCategory(id);
        ActionUtils.setUpPaginationForAllCategories(req, adminService);
        req.setAttribute(MESSAGE, SUCCESSFUL);
        return MANAGE_CATEGORIES_PAGE;
    }
}
