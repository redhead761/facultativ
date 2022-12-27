package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ManageStudentsAction implements Action {
    private final AdminService adminService;

    public ManageStudentsAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        removeRedundantAttribute(req);
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        ActionUtils.setUpPaginationForStudents(req, adminService, currentPage, recordsPerPage);
        return MANAGE_STUDENTS_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("sort_type");
        req.getSession().removeAttribute("select_type");
        req.getSession().removeAttribute("currentPage");
        req.getSession().removeAttribute("message");
    }

}
