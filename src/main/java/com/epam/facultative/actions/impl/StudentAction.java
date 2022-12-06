package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;

public class StudentAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        return "student.jsp";
    }
}
