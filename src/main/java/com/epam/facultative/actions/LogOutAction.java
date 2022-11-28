package com.epam.facultative.actions;

import jakarta.servlet.http.HttpServletRequest;

public class LogOutAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return "auth.jsp";
    }
}
