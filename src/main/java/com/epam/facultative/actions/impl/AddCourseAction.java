package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.facultative.actions.impl.Constants.*;

public class AddCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req,HttpServletResponse resp) {
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
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        } catch (ValidateException e) {
            req.setAttribute("title", title);
            req.setAttribute("duration", duration);
            req.setAttribute("start_date", date);
            req.setAttribute("description", description);
            path = COURSE_FORM_PAGE;
            req.setAttribute("message", e.getMessage());
        }
//        catch (RuntimeException e) {
//            path = COURSE_FORM_PAGE;
//            req.setAttribute("message", "Incorrect duration or start date");
//        }
        return path;
    }
}

