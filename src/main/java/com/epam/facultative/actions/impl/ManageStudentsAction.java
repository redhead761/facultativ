package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;

public class ManageStudentsAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            req.setAttribute("students", generalService.getAllStudents());
            path = MANAGE_STUDENTS_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
