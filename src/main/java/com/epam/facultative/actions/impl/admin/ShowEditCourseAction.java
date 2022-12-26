package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;

public class ShowEditCourseAction implements Action {
    private final GeneralService generalService;
    private final AdminService adminService;

    public ShowEditCourseAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        removeRedundantAttribute(req);
        int courseId;
        if (req.getParameter("course_id") != null) {
            courseId = Integer.parseInt(req.getParameter("course_id"));
            req.getSession().setAttribute("course_id", courseId);
        } else {
            courseId = (int) req.getSession().getAttribute("course_id");
        }
        CourseDTO course = adminService.getCourse(courseId);
        req.getSession().setAttribute("title", course.getTitle());
        req.getSession().setAttribute("duration", course.getDuration());
        req.getSession().setAttribute("start_date", course.getStartDate());
        req.getSession().setAttribute("description", course.getDescription());
        req.getSession().setAttribute("category", course.getCategory().getTitle());
        req.getSession().setAttribute("status", course.getStatus());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        return EDIT_COURSE_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("sort_type");
        req.getSession().removeAttribute("select_type");
    }
}
