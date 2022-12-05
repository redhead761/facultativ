package com.epam.facultative.actions;

import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class LogOutAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().invalidate();
        return AUTH_PAGE;
    }
}
