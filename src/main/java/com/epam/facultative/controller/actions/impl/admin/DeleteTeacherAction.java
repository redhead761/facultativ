package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.MANAGE_TEACHERS_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;
/**
 * Accessible by admin. Allows to delete teacher from database.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class DeleteTeacherAction implements Action {
    AdminService adminService;

    public DeleteTeacherAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }
    /**
     * Called from doPost method in front-controller. Tries to delete teacher from database.
     * Return error message if not able
     *
     * @param req to get users id and set message in case of successful deleted
     * @return path to redirect to executeGet method through front-controller
     */
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

