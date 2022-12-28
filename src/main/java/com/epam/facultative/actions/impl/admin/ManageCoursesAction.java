package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionFactory;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ManageCoursesAction implements Action {
    private final GeneralService generalService;

    public ManageCoursesAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
//        if (req.getSession().getAttribute("sort_type") != null) {
//            ActionFactory.getActionFactory().getAction("sort").execute(req, resp);
//            return MANAGE_COURSES_PAGE;
//        }
//        if (req.getSession().getAttribute("select_type") != null) {
//            ActionFactory.getActionFactory().getAction("select_courses").execute(req, resp);
//            return MANAGE_COURSES_PAGE;
//        }
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        ActionUtils.setUpPaginationForCourses(req, generalService, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
        return MANAGE_COURSES_PAGE;
    }

}
