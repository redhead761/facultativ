package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.PageNameConstants.*;

public class BlockStudentAction implements Action {
    private final AdminService adminService;

    public BlockStudentAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        adminService.blockStudent(Integer.parseInt(req.getParameter("student_id")));
        int page = (int) req.getSession().getAttribute("currentPage");
        int recordsPerPage = 5;
        req.getSession().setAttribute("students", adminService.getAllStudentsPagination((page - 1) * recordsPerPage, recordsPerPage));
        return MANAGE_STUDENTS_PAGE;
    }
}
