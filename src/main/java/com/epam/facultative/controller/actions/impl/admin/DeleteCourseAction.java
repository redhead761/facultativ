package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.controller.actions.PageNameConstants.*;
import static com.epam.facultative.controller.AttributeConstants.*;

public class DeleteCourseAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public DeleteCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int id = Integer.parseInt(req.getParameter(COURSE_ID));
        adminService.deleteCourse(id);
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        ActionUtils.setUpPaginationForAllCourses(req, generalService);
        return MANAGE_COURSES_PAGE;
    }
}
