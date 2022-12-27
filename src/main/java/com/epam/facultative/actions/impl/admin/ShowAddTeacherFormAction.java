package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowAddTeacherFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ADD_TEACHER_PAGE;
    }
}
