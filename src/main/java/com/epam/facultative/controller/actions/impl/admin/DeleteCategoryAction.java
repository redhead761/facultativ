package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class DeleteCategoryAction implements Action {
    private final AdminService adminService;

    public DeleteCategoryAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        int id = Integer.parseInt(req.getParameter("category_id"));
        adminService.deleteCategory(id);
        req.setAttribute("categories", adminService.getAllCategoriesPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsCategories();
        ActionUtils.setUpPaginationForCategories(req, noOfRecords, currentPage, recordsPerPage);
        req.setAttribute("message", "Successful");
        return MANAGE_CATEGORIES_PAGE;
    }
}
