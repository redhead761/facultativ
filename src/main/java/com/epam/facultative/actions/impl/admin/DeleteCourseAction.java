package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class DeleteCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int id = Integer.parseInt(req.getParameter("course_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        adminService.deleteCourse(id);
        req.getSession().setAttribute("courses", generalService.getAllCourses(0,3));
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        req.getSession().setAttribute("message", "Successful");
        return ADMIN_PAGE;
    }
}
