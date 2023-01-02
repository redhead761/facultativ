package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.PageNameConstants.*;

public class DeleteCategoryAction implements Action {
    private final AdminService adminService;

    public DeleteCategoryAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        int id = Integer.parseInt(req.getParameter("category_id"));
        adminService.deleteCategory(id);
        ActionUtils.setUpPaginationForCategories(req, adminService, currentPage, recordsPerPage);
        req.setAttribute("message", "Successful");
        return MANAGE_CATEGORIES_PAGE;
    }
}
