package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowEditCourseAction implements Action {
    private final GeneralService generalService;
    private final AdminService adminService;

    public ShowEditCourseAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        CourseDTO course = adminService.getCourse(courseId);
        req.setAttribute("title", course.getTitle());
        req.setAttribute("duration", course.getDuration());
        req.setAttribute("start_date", course.getStartDate());
        req.setAttribute("description", course.getDescription());
        req.setAttribute("category", course.getCategory().getTitle());
        req.setAttribute("status", course.getStatus());
        req.setAttribute("categories", generalService.getAllCategories());
        return EDIT_COURSE_PAGE;
    }
}
