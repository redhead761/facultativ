package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.actions.ActionUtils.setAllCourses;
import static com.epam.facultative.controller.constants.PageNameConstants.COURSES_PAGE;

public class CoursesAction implements Action {
    private final GeneralService generalService;

    public CoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        setAllCourses(req, generalService);
        return COURSES_PAGE;
    }
}

