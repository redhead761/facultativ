package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.COURSE_ID;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowAssignPageAction implements Action {
    private final AdminService adminService;

    public ShowAssignPageAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        req.setAttribute(COURSE_ID, courseId);
        ActionUtils.setUpPaginationForAllTeachers(req, adminService);
        return ASSIGN_PAGE;
    }
}
