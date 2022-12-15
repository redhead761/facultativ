package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class DeleteCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int id = Integer.parseInt(req.getParameter("category_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        adminService.deleteCategory(id);
        req.setAttribute("message", "Successful");
        return MANAGE_CATEGORIES_ACTION;
    }
}
