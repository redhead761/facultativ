package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class DeleteCourseAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public DeleteCourseAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        removeRedundantAttribute(req);
        int page = (int) req.getSession().getAttribute("currentPage");
        int recordsPerPage = 5;
        int id = Integer.parseInt(req.getParameter("course_id"));
        adminService.deleteCourse(id);
        req.getSession().setAttribute("courses", generalService.getAllCourses((page - 1) * recordsPerPage, recordsPerPage));
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        req.getSession().setAttribute("message", "Successful");
        return MANAGE_COURSES_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("sort_type");
        req.getSession().removeAttribute("select_type");
    }
}
