package com.epam.facultative.actions.impl.student;

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

public class ShowStudentCabinetAction implements Action {
    private final GeneralService generalService;

    public ShowStudentCabinetAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        removeRedundantAttribute(req);
        if (req.getSession().getAttribute("sort_type") != null) {
            ActionFactory.getActionFactory().getAction("sort").execute(req, resp);
            return STUDENT_PAGE;
        }
        if (req.getSession().getAttribute("select_type") != null) {
            ActionFactory.getActionFactory().getAction("select_courses").execute(req, resp);
            return STUDENT_PAGE;
        }
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        ActionUtils.setUpPaginationForCourses(req, generalService, currentPage, recordsPerPage);
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        return STUDENT_PAGE;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("message");
    }
}

