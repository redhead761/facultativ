package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.actions.PageNameConstants.*;
import static com.epam.facultative.controller.AttributeConstants.*;

public class AddCategoryAction implements Action {

    private final AdminService adminService;

    public AddCategoryAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        CategoryDTO category = getCategoryFromParameter(req);
        try {
            adminService.addCategory(category);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(CATEGORY, category);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return ADD_CATEGORY_PAGE;
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
