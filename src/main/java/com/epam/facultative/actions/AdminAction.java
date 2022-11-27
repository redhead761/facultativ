package com.epam.facultative.actions;

import jakarta.servlet.http.HttpServletRequest;

public class AdminAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
         return "admin.jsp";
    }
}
