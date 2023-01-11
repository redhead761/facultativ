package com.epam.facultative.controller.actions.impl.teacher;

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

public class AllCoursesAction implements Action {
    private final GeneralService generalService;

    public AllCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
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
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);
        req.setAttribute("courses", generalService.getAllCourses((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = generalService.getNoOfRecordsCourses();
        ActionUtils.setUpPaginationForCourses(req, noOfRecords, currentPage, recordsPerPage);
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        return TEACHER_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("message");
    }
}
