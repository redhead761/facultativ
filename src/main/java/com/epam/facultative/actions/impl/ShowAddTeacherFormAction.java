package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;

public class ShowAddTeacherFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        return ADD_TEACHER_PAGE;
    }
}
