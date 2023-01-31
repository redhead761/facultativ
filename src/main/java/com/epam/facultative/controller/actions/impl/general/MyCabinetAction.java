package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.entities.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

/**
 * Accessible by general. Allows to show my cabinet.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class MyCabinetAction implements Action {
    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param req to get role
     * @return cabinet page from role after trying go to cabinet
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        String path = null;
        Role role = (Role) req.getSession().getAttribute(ROLE);
        switch (role) {
            case ADMIN -> path = ADMIN_PROFILE_PAGE;
            case TEACHER -> path = TEACHER_PROFILE_PAGE;
            case STUDENT -> path = STUDENT_PROFILE_PAGE;
        }
        return path;
    }
}
