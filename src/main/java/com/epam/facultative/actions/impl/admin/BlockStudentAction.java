package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class BlockStudentAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        adminService.blockStudent(studentId);
        req.getSession().setAttribute("students", adminService.getAllStudents());
        return MANAGE_STUDENTS_PAGE;
    }
}
