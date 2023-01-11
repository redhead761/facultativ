package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class AssignAction implements Action {
    private final AdminService adminService;

    public AssignAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
        try {
            adminService.assigned(courseId, teacherId);
        } catch (ValidateException e) {
            throw new ServiceException(e);
        }
        req.getSession().setAttribute("message", "Successful");
        req.setAttribute("course_id", courseId);
        ActionUtils.setUpPaginationForTeachers(req, adminService);
        return ASSIGN_PAGE;
    }
}
