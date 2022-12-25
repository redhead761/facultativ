package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class AssignAction implements Action {
    private final AdminService adminService;

    public AssignAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = (int) req.getSession().getAttribute("course_id");
        int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
        adminService.assigned(courseId, teacherId);
        req.getSession().setAttribute("message", "Successful");
        return SHOW_ASSIGN_PAGE_ACTION;
    }
}
