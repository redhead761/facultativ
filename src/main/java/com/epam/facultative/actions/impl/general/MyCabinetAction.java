package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entities.Role;
import com.epam.facultative.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;
import static com.epam.facultative.entities.Role.*;

public class MyCabinetAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path = null;
        Role role = (Role) req.getSession().getAttribute("role");

        switch (role) {
            case ADMIN -> path = ADMIN_PROFILE_PAGE;
            case TEACHER -> path = TEACHER_PROFILE_PAGE;
            case STUDENT -> path = STUDENT_PROFILE_PAGE;
        }
        return path;
    }
}
