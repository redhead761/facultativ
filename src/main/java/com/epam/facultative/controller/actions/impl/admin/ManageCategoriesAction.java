package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.categoryParamBuilderForQuery;

public class ManageCategoriesAction implements Action {
    private final GeneralService generalService;

    public ManageCategoriesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE, ERROR);
        ParamBuilderForQuery paramBuilderForQuery = categoryParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<CategoryDTO>> categoriesWithRows = generalService.getCategories(paramBuilderForQuery.getParam());
        req.setAttribute(CATEGORIES, categoriesWithRows.getValue());
        setUpPaginate(req, categoriesWithRows.getKey());
        return MANAGE_CATEGORIES_PAGE;
    }
}
