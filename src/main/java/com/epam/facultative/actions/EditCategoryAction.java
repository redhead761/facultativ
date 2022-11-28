package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class EditCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String categoryId = req.getParameter("category_id");
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            Category category = adminService.getCategory(Integer.parseInt(categoryId));
            req.setAttribute("category", category);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "category_form.jsp";
    }
}
