package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.facultative.actions.Constants.*;

public class UpdateCourseAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public UpdateCourseAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Course course = getCourseFromParameter(req);
        try {
            adminService.updateCourse(course);
            req.getSession().setAttribute("message", "Successful");
        } catch (ValidateException e) {
            req.getSession().setAttribute("message", e.getMessage());
            return COURSE_FORM_ACTION;
        }
        return MANAGE_COURSES_ACTION;
    }

    private Course getCourseFromParameter(HttpServletRequest req) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");
        Status status = Status.valueOf(req.getParameter("status"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        Category category = adminService.getCategory(categoryId);
        return new Course(courseId, title, duration, date, description, category, status);
    }
}

