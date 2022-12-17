package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionFactory;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;

public class ManageCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        if (req.getSession().getAttribute("sort_type") != null) {
            ActionFactory.getActionFactory().getAction("sort").execute(req, resp);
            return ADMIN_PAGE;
        }
        if (req.getSession().getAttribute("select_type") != null) {
            ActionFactory.getActionFactory().getAction("select_courses").execute(req, resp);
            return ADMIN_PAGE;
        }
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        req.getSession().setAttribute("courses", generalService.getAllCourses((page - 1) * recordsPerPage, recordsPerPage));

        int noOfRecords = generalService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCoursesPages", noOfPages);
        req.getSession().setAttribute("currentPage", page);
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        return ADMIN_PAGE;
    }
}
