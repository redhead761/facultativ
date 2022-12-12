package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.impl.Constants.*;

public class DeleteCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String path;
        int id = Integer.parseInt(req.getParameter("category_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.deleteCategory(id);
            req.setAttribute("message", "Successful");
            path = MANAGE_CATEGORIES_ACTION;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", "Successful");
        }
        return path;
    }
}
