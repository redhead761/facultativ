package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class ShowAssignPageAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        removeRedundantAttribute(req);
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        int courseId;
        if (req.getParameter("course_id") != null) {
            courseId = Integer.parseInt(req.getParameter("course_id"));
            CourseDTO course = adminService.getCourse(courseId);
            req.getSession().setAttribute("course", course);
        }

        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        req.getSession().setAttribute("teachers", adminService.getAllTeachersPagination((page - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsTeachers();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfTeachersPages", noOfPages);
        req.getSession().setAttribute("currentPage", page);
        return ASSIGN_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("sort_type");
        req.getSession().removeAttribute("select_type");
    }
}
