package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionFactory;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowAllCoursesAction implements Action {
    private final GeneralService generalService;

    public ShowAllCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        String sortType = req.getParameter("sort_type");
        String selectType = req.getParameter("select_type");
        if (sortType != null && !sortType.isBlank()) {
            ActionFactory.getActionFactory().getAction("sort").execute(req, resp);
            return STUDENT_PAGE;
        }
        if (selectType != null && !selectType.isBlank()) {
            ActionFactory.getActionFactory().getAction("select_courses").execute(req, resp);
            return STUDENT_PAGE;
        }
        ActionUtils.setUpPaginationForAllCourses(req, generalService);
        return STUDENT_PAGE;
    }
}

