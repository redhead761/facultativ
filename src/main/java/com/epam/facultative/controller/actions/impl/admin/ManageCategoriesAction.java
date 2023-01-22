package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.utils.query_builders.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;
import static com.epam.facultative.utils.query_builders.ParamBuilderForQueryUtil.categoryParamBuilderForQuery;

public class ManageCategoriesAction implements Action {
    private final AdminService adminService;

    public ManageCategoriesAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE);
        ParamBuilderForQuery paramBuilderForQuery = categoryParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<CategoryDTO>> categoriesWithRows = adminService.getAllCategoriesPagination(paramBuilderForQuery.getParam());
        req.setAttribute(CATEGORIES, categoriesWithRows.getValue());
        testSetUp(req, categoriesWithRows.getKey());
        return MANAGE_CATEGORIES_PAGE;
    }
}
