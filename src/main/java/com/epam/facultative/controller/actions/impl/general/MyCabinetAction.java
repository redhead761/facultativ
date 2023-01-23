package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.data_layer.entities.User;
import com.epam.facultative.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.USER;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class MyCabinetAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path = null;
        User user = (User) req.getSession().getAttribute(USER);
        switch (user.getRole()) {
            case ADMIN -> path = ADMIN_PROFILE_PAGE;
            case TEACHER -> path = TEACHER_PROFILE_PAGE;
            case STUDENT -> path = STUDENT_PROFILE_PAGE;
        }
        return path;
    }
}
