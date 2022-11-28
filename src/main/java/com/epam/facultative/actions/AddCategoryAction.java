package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AddCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
         if (req.getParameter("title") != null) {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            Category category = new Category();
            category.setTitle(title);
            category.setDescription(description);
            try {
                adminService.addCategory(category);
                req.setAttribute("message", "Successful");
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            } catch (ValidateException e) {
                req.setAttribute("message", e.getMessage());
            }
        }

         return "category_form.jsp";
    }
}
