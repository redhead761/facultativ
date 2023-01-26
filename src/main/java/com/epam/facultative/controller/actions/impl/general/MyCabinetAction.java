package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class MyCabinetAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        String path = null;
        Role role = (Role) req.getSession().getAttribute(ROLE);
        switch (role) {
            case ADMIN -> path = ADMIN_PROFILE_PAGE;
            case TEACHER -> path = TEACHER_PROFILE_PAGE;
            case STUDENT -> path = STUDENT_PROFILE_PAGE;
        }
        return path;
    }
}
