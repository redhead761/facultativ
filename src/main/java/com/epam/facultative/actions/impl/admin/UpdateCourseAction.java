package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entities.Category;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.facultative.actions.PageNameConstants.*;

public class UpdateCourseAction implements Action {
    private final AdminService adminService;

    public UpdateCourseAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
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
        req.getSession().removeAttribute("course_id");
        return MANAGE_COURSES_ACTION;
    }

    private Course getCourseFromParameter(HttpServletRequest req) throws ServiceException {
        String id = (String) req.getSession().getAttribute("course_id");
        int courseId = Integer.parseInt(id);
        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");
        Status status = Status.valueOf(req.getParameter("status"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        Category category = adminService.getCategory(categoryId);
        return Course.builder()
                .id(courseId)
                .title(title)
                .duration(duration)
                .startDate(date)
                .description(description)
                .category(category)
                .status(status)
                .build();
    }
}

