package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class ManageCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int recordsPerPage = 5;

        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        req.getSession().setAttribute("courses", generalService.getAllCourses((page - 1) * recordsPerPage, recordsPerPage));

        int noOfRecords = generalService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCoursesPages", noOfPages);
        req.getSession().setAttribute("currentPage", page);

        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        return ADMIN_PAGE;
    }
}
