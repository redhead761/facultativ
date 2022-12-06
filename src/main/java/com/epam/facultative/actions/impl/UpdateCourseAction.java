package com.epam.facultative.actions.impl;

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

import java.time.LocalDate;

import static com.epam.facultative.actions.impl.Constants.*;

public class UpdateCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
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
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        } catch (ValidateException e) {
            path = COURSE_FORM_ACTION;
            req.setAttribute("message", e.getMessage());
        } catch (RuntimeException e) {
            path = COURSE_FORM_ACTION;
            req.setAttribute("message", "Incorrect duration or start date");
        }
        return path;
    }
}

