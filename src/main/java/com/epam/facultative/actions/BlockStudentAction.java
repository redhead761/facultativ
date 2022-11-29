package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class BlockStudentAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            adminService.blockStudent(studentId);
            req.setAttribute("students", adminService.getAllStudents());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_students.jsp";
    }
}
