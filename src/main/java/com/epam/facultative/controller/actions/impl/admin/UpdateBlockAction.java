package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.MANAGE_STUDENTS_ACTION;

public class UpdateBlockAction implements Action {
    private final AdminService adminService;

    public UpdateBlockAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String type = req.getParameter(TYPE);
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);
        int studentId = Integer.parseInt(req.getParameter(STUDENT_ID));
        switch (type) {
            case "block" -> adminService.blockStudent(studentId);
            case "unblock" -> adminService.unblockStudent(studentId);
        }
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        return ActionUtils.getGetAction(MANAGE_STUDENTS_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage);
    }
}
