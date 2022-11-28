package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String id = req.getParameter("category_id");
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.deleteCategory(Integer.parseInt(id));
            req.setAttribute("categories", adminService.getAllCategories());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_categories.jsp";
    }
}
