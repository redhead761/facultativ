package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entities.Category;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.facultative.actions.ActionNameConstants.CONTROLLER;
import static com.epam.facultative.actions.ActionNameConstants.SHOW_ADD_COURSE_ACTION;
import static com.epam.facultative.actions.PageNameConstants.*;

public class AddCourseAction implements Action {

    private final AdminService adminService;
    private final GeneralService generalService;

    public AddCourseAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        Course course = getCourseFromParameter(req);
        try {
            adminService.addCourse(course);
            req.getSession().setAttribute("message", "Successful");
        } catch (ValidateException e) {
            req.getSession().setAttribute("message", e.getMessage());
//            req.setAttribute("title", course.getTitle());
//            req.setAttribute("duration", course.getDuration());
//            req.setAttribute("start_date", course.getStartDate());
//            req.setAttribute("description", course.getDescription());
//            req.setAttribute("category", course.getCategory().getTitle());
//            req.setAttribute("status", course.getStatus());
//            req.setAttribute("categories", generalService.getAllCategories());
//            req.getRequestDispatcher(ADD_COURSE_PAGE).forward(req, resp);
        }
        return CONTROLLER + SHOW_ADD_COURSE_ACTION;
    }

    private Course getCourseFromParameter(HttpServletRequest req) throws ServiceException {
        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");
        Status status = Status.valueOf(req.getParameter("status"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        Category category = adminService.getCategory(categoryId);
        return Course.builder()
                .title(title)
                .duration(duration)
                .startDate(date)
                .description(description)
                .category(category)
                .status(status)
                .build();
    }
}

