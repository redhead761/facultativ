package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.controller.actions.ActionNameConstants.MANAGE_COURSES_ACTION;
import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;

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
        String sort = req.getParameter(SORT);
        String selectByCategory = req.getParameter(SELECT_BY_CATEGORY);
        String selectByTeacher = req.getParameter(SELECT_BY_TEACHER);
        adminService.deleteCourse(id);
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        return getGetAction(MANAGE_COURSES_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage, SORT, sort, SELECT_BY_CATEGORY, selectByCategory, SELECT_BY_TEACHER, selectByTeacher);
    }
}
