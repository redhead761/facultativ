package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class LogOutAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String locale = (String) req.getSession().getAttribute("language");
        req.getSession().invalidate();
        req.getSession().setAttribute("language", locale);
        return AUTH_PAGE;
    }
}
