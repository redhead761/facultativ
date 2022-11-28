package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class UpdateCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String id = req.getParameter("category_id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Category category = new Category(Integer.parseInt(id), title, description);
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.updateCategory(category);
            req.setAttribute("message", "Successful");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (ValidateException e) {
            req.setAttribute("message", e.getMessage());
        }
        return "category_form.jsp";
    }
}
