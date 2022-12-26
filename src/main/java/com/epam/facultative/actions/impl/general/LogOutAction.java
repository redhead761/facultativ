package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class LogOutAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("user") != null) {
            req.getSession().invalidate();
        }
        return AUTH_PAGE;
    }
}
