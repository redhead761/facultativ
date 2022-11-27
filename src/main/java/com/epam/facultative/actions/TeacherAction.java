package com.epam.facultative.actions;

import jakarta.servlet.http.HttpServletRequest;

public class TeacherAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        return "teacher.jsp";
    }
}
