package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.ActionUtils.setAllCourses;
import static com.epam.facultative.controller.constants.PageNameConstants.COURSES_PAGE;

/**
 * Accessible by general. Allows to course list from database.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class CoursesAction implements Action {
    private final GeneralService generalService;

    public CoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    /**
     * Called from doGet method in front-controller. Obtains required path.
     *
     * @param req to get parameters and set courses
     * @return courses page after trying to show courses
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        setAllCourses(req, generalService);
        return COURSES_PAGE;
    }
}

