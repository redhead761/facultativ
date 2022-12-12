package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.impl.Constants.ERROR_PAGE;
import static com.epam.facultative.actions.impl.Constants.MANAGE_STUDENTS_PAGE;

public class UnblockStudentAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String path;
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.unblockStudent(studentId);
            req.setAttribute("students", adminService.getAllStudents());
            path = MANAGE_STUDENTS_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
