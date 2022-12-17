package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class UnblockStudentAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int studentId = Integer.parseInt(req.getParameter("student_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        adminService.unblockStudent(studentId);
        int page = (int) req.getSession().getAttribute("currentPage");
        int recordsPerPage = 5;
        req.getSession().setAttribute("students", adminService.getAllStudentsPagination((page - 1) * recordsPerPage, recordsPerPage));
        return MANAGE_STUDENTS_PAGE;
    }
}
