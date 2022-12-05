package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class AddCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        try {
            adminService.addCategory(category);
            req.setAttribute("message", "Successful");
            path = CATEGORY_FORM_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        } catch (ValidateException e) {
            path = CATEGORY_FORM_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
