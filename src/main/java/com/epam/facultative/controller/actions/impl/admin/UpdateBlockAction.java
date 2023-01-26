package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.MANAGE_STUDENTS_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;

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
        return getGetAction(MANAGE_STUDENTS_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage);
    }
}
