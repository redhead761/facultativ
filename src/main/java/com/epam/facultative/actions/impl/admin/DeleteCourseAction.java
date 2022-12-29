package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.PageNameConstants.*;

public class DeleteCourseAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public DeleteCourseAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int id = Integer.parseInt(req.getParameter("course_id"));
        adminService.deleteCourse(id);
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        ActionUtils.setUpPaginationForCourses(req, generalService, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
        return MANAGE_COURSES_PAGE;
    }
}
