package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.actions.PageNameConstants.*;
import static com.epam.facultative.controller.AttributeConstants.*;

public class ShowEditCourseAction implements Action {
    private final GeneralService generalService;
    private final AdminService adminService;

    public ShowEditCourseAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        CourseDTO course = null;
        try {
            course = adminService.getCourse(courseId);
        } catch (ValidateException e) {
            req.getSession().setAttribute(MESSAGE, e.getMessage());
        }
        req.getSession().setAttribute(COURSE, course);
        req.setAttribute(CATEGORIES, generalService.getAllCategories().getValue());
        req.setAttribute(TEACHERS, generalService.getAllTeachers().getValue());
        return EDIT_COURSE_PAGE;
    }
}
