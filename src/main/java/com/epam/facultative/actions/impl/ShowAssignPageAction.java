package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.epam.facultative.actions.impl.Constants.*;

public class ShowAssignPageAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String path;
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            CourseDTO course = adminService.getCourse(courseId);
            List<UserDTO> teachers = adminService.getAllTeachers();
            req.setAttribute("course", course);
            req.setAttribute("teachers", teachers);
            path = ASSIGN_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
