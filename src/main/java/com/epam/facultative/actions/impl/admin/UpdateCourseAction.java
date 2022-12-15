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

public class UpdateCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path;
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();

        try {
            int courseId = Integer.parseInt(req.getParameter("course_id"));
            req.setAttribute("categories", generalService.getAllCategories());

            String title = req.getParameter("title");
            int duration = Integer.parseInt(req.getParameter("duration"));
            LocalDate date = LocalDate.parse(req.getParameter("start_date"));
            String description = req.getParameter("description");
            Category category = adminService.getCategory(Integer.parseInt(req.getParameter("category")));
            Status status = Status.valueOf(req.getParameter("status"));

            Course course = new Course(title, duration, date, description, category, status);
            course.setId(courseId);
            adminService.updateCourse(course);
            req.setAttribute("message", "Successful");
            path = MANAGE_COURSES_ACTION;

        } catch (ValidateException e) {
            path = COURSE_FORM_ACTION;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}

