package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowAddCourseAction implements Action {
    private final GeneralService generalService;

    public ShowAddCourseAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        req.setAttribute("categories", generalService.getAllCategories());
        return ADD_COURSE_PAGE;
    }
}
