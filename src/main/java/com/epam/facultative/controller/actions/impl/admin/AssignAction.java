package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.ActionNameConstants.ASSIGN_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.ASSIGN_PAGE;

/**
 * Accessible by admin. Allows to assign teacher to course from database. Implements PRG pattern
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class AssignAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public AssignAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param req to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);

    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request. Get and set all teachers
     *
     * @param req to get message attribute from session and put it in request
     * @return assign  page after trying to assign
     */
    private String executeGet(HttpServletRequest req) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        req.setAttribute(COURSE_ID, courseId);
        setAllTeachers(req, generalService);
        transferAttributeFromSessionToRequest(req, MESSAGE);
        return ASSIGN_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to assign teacher to course from database.
     * Return error message if not able
     *
     * @param req to get users id and set message in case of successful added
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String courseId = req.getParameter(COURSE_ID);
        String teacherId = req.getParameter(TEACHER_ID);
        try {
            adminService.assigned(Integer.parseInt(courseId), Integer.parseInt(teacherId));
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ASSIGN_ACTION, COURSE_ID, courseId);
    }
}
