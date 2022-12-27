package com.epam.facultative.actions.impl.teacher;

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

public class AllCoursesAction implements Action {
    private final GeneralService generalService;

    public AllCoursesAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, ServiceException, IOException {
        removeRedundantAttribute(req);
        if (req.getSession().getAttribute("sort_type") != null) {
            ActionFactory.getActionFactory().getAction("sort").execute(req, resp);
            return TEACHER_PAGE;
        }
        if (req.getSession().getAttribute("select_type") != null) {
            ActionFactory.getActionFactory().getAction("select_courses").execute(req, resp);
            return TEACHER_PAGE;
        }
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        ActionUtils.setUpPaginationForCourses(req, generalService, currentPage, recordsPerPage);
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        return TEACHER_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("message");
    }
}
