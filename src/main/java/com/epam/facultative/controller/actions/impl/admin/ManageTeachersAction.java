package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

/**
 * Accessible by admin. Allows to show manage teachers
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ManageTeachersAction implements Action {
    private final GeneralService generalService;

    public ManageTeachersAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    /**
     * Called from doPost method in front-controller.Transfer attributes from session to request. Get and set teachers
     *
     * @param req to set teachers
     * @return manage teachers page
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        setAllTeachers(req, generalService);
        return MANAGE_TEACHERS_PAGE;
    }
}
