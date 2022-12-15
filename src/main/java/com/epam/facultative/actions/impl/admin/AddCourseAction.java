package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.*;
import com.epam.facultative.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.facultative.actions.Constants.*;

public class AddCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path;

        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");

        try {
            AdminService adminService = ServiceFactory.getInstance().getAdminService();
            req.setAttribute("categories", adminService.getAllCategories());
            Category category = adminService.getCategory(Integer.parseInt(req.getParameter("category")));
            Status status = Status.valueOf(req.getParameter("status"));
            Course course = new Course(title, duration, date, description, category, status);
            adminService.addCourse(course);
            req.setAttribute("message", "Successful");
            path = COURSE_FORM_PAGE;
        } catch (ValidateException e) {
            req.setAttribute("title", title);
            req.setAttribute("duration", duration);
            req.setAttribute("start_date", date);
            req.setAttribute("description", description);
            path = COURSE_FORM_PAGE;
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(path).forward(req, resp);
        }
        return path;
    }
}

