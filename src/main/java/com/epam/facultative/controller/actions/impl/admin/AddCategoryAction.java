package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.constants.ActionNameConstants.ADD_CATEGORY_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;

public class AddCategoryAction implements Action {

    private final AdminService adminService;

    public AddCategoryAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, CATEGORY);
        return ADD_CATEGORY_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        CategoryDTO category = getCategoryFromParameter(req);
        try {
            adminService.addCategory(category);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(CATEGORY, category);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ADD_CATEGORY_ACTION);
    }

    private CategoryDTO getCategoryFromParameter(HttpServletRequest req) {
        String title = req.getParameter(TITLE);
        String description = req.getParameter(DESCRIPTION);
        return CategoryDTO.builder()
                .id(0)
                .title(title)
                .description(description)
                .build();
    }
}
