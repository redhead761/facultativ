package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class ShowAddTeacherFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ADD_TEACHER_PAGE;
    }
}
