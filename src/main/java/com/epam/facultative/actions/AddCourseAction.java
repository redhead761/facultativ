package com.epam.facultative.actions;

import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

public class AddCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {

        try {
            AdminService adminService = ServiceFactory.getInstance().getAdminService();
            List<Category> categories = adminService.getAllCategories();
            req.setAttribute("categories", categories);

            String title = req.getParameter("title");
            int duration = Integer.parseInt(req.getParameter("duration"));
            LocalDate date = LocalDate.parse(req.getParameter("start_date"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("category"));
            Status status = Status.valueOf(req.getParameter("status"));
            Course course = new Course();
            course.setTitle(title);
            course.setDuration(duration);
            course.setStartDate(date);
            course.setDescription(description);
            course.setStatus(status);
            course.setCategory(adminService.getCategory(categoryId));
            adminService.addCourse(course);
            req.setAttribute("message", "Successful");
        } catch (ServiceException e) {
            req.setAttribute("message", e.getMessage());
        } catch (ValidateException e) {
            req.setAttribute("message", e.getMessage());
        } catch (RuntimeException e) {
            req.setAttribute("message", "ooops");
        }
        return "course_form.jsp";
    }
}

