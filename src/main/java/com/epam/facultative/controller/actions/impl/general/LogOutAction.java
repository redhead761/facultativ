package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.USER;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class LogOutAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute(USER) != null) {
            req.getSession().invalidate();
        }
        return AUTH_PAGE;
    }
}
