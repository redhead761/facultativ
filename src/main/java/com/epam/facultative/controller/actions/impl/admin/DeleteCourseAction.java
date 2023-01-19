package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.controller.actions.ActionNameConstants.MANAGE_COURSES_ACTION;
import static com.epam.facultative.controller.AttributeConstants.*;

public class DeleteCourseAction implements Action {
    private final AdminService adminService;

    public DeleteCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int id = Integer.parseInt(req.getParameter(COURSE_ID));
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);
        String sortType = req.getParameter(SORT_TYPE);
        String selectType = req.getParameter(SELECT_TYPE);
        adminService.deleteCourse(id);
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        return ActionUtils.getGetAction(MANAGE_COURSES_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage, SORT_TYPE, sortType, SELECT_TYPE, selectType);
    }
}
