package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.LANGUAGE;
import static com.epam.facultative.controller.constants.AttributeConstants.USER;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

/**
 * Accessible by general. Allows to log out.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class LogOutAction implements Action {
    /**
     * Called from doGet method in front-controller. Invalidate from session
     *
     * @param req to get session
     * @return auth page after trying to log out
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute(USER) != null) {
            String locale = (String) req.getSession().getAttribute(LANGUAGE);
            req.getSession().invalidate();
            req.getSession(true).setAttribute(LANGUAGE, locale);
        }
        return AUTH_PAGE;
    }
}
