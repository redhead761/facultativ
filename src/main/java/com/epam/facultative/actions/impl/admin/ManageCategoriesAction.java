package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ManageCategoriesAction implements Action {
    private final AdminService adminService;

    public ManageCategoriesAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        ActionUtils.setUpPaginationForCategories(req, adminService, currentPage, recordsPerPage);
        return MANAGE_CATEGORIES_PAGE;
    }

}
