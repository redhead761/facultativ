package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.MANAGE_TEACHERS_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;

public class DeleteTeacherAction implements Action {
    AdminService adminService;

    public DeleteTeacherAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String id = req.getParameter(TEACHER_ID);
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);
        try {
            adminService.deleteTeacher(Integer.parseInt(id));
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(MANAGE_TEACHERS_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage);
    }
}

