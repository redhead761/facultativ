package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class UpdateBlockAction implements Action {
    private final AdminService adminService;

    public UpdateBlockAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String type = req.getParameter(TYPE);
        int studentId = Integer.parseInt(req.getParameter(STUDENT_ID));
        switch (type) {
            case "block" -> adminService.blockStudent(studentId);
            case "unblock" -> adminService.unblockStudent(studentId);
        }
        req.setAttribute(MESSAGE, SUCCESSFUL);
        ActionUtils.setUpPaginationForAllStudents(req, adminService);
        return MANAGE_STUDENTS_PAGE;
    }
}
