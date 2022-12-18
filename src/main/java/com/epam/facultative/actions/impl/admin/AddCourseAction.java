package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.facultative.actions.Constants.*;

public class AddCourseAction implements Action {

    private final AdminService adminService;

    public AddCourseAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Course course = getCourseFromParameter(req);
        try {
            adminService.addCourse(course);
            req.setAttribute("message", "Successful");
        } catch (ValidateException e) {
            req.setAttribute("title", course.getTitle());
            req.setAttribute("duration", course.getDuration());
            req.setAttribute("start_date", course.getStartDate());
            req.setAttribute("description", course.getDescription());
            req.setAttribute("message", e.getMessage());
        }
        return COURSE_FORM_PAGE;
    }

    private Course getCourseFromParameter(HttpServletRequest req) throws ServiceException {
        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");
        Status status = Status.valueOf(req.getParameter("status"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        Category category = adminService.getCategory(categoryId);
        return new Course(title, duration, date, description, category, status);
    }
}

