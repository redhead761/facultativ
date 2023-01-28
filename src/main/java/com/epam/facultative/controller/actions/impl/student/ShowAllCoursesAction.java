package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setAllCourses;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

public class ShowAllCoursesAction implements Action {
    private final GeneralService generalService;

    public ShowAllCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE, ERROR);
        setAllCourses(req, generalService);
        return STUDENT_PAGE;
    }
}

