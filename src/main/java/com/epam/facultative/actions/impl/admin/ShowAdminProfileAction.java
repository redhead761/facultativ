package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowAdminProfileAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return ADMIN_PROFILE_PAGE;
    }
}
