package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowEditCourseAction implements Action {
    private final GeneralService generalService;
    private final AdminService adminService;

    public ShowEditCourseAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        CourseDTO course = adminService.getCourse(courseId);
        req.setAttribute("course", course);
        req.setAttribute("categories", generalService.getAllCategories());
        req.setAttribute("teachers", generalService.getAllTeachers());
        return EDIT_COURSE_PAGE;
    }
}
