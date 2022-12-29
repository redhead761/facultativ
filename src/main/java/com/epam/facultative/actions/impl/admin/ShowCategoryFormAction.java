package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.entities.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowCategoryFormAction implements Action {
    private final AdminService adminService;

    public ShowCategoryFormAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int categoryId = Integer.parseInt(req.getParameter("category_id"));
        Category category = adminService.getCategory(categoryId);
        req.setAttribute("title", category.getTitle());
        req.setAttribute("description", category.getDescription());
        req.setAttribute("category_id", categoryId);
        return CATEGORY_FORM_PAGE;
    }
}
