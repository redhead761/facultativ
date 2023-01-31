package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setAllCourses;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

/**
 * Accessible by admin. Allows to show manage course
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ManageCoursesAction implements Action {
    private final GeneralService generalService;

    public ManageCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    /**
     * Called from doPost method in front-controller. Transfer attributes from session to request. Get and set courses
     *
     * @param req to set courses
     * @return manage courses page
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE);
        setAllCourses(req, generalService);
        return MANAGE_COURSES_PAGE;
    }
}
