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

import static com.epam.facultative.actions.Constants.COURSE_FORM_PAGE;
import static com.epam.facultative.actions.Constants.ERROR_PAGE;

public class UpdateCourseAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        AdminService adminService = ServiceFactory.getInstance().getAdminService();

        try {
            int id = Integer.parseInt(req.getParameter("course_id"));
            List<Category> categories = adminService.getAllCategories();
            req.setAttribute("categories", categories);

            String title = req.getParameter("title");
            int duration = Integer.parseInt(req.getParameter("duration"));
            LocalDate date = LocalDate.parse(req.getParameter("start_date"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("category"));
            Status status = Status.valueOf(req.getParameter("status"));
            Course course = new Course();
            course.setId(id);
            course.setTitle(title);
            course.setDuration(duration);
            course.setStartDate(date);
            course.setDescription(description);
            course.setStatus(status);

            course.setCategory(adminService.getCategory(categoryId));
            adminService.updateCourse(course);
            req.setAttribute("message", "Successful");
            path = "controller?action=manage_courses";
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        } catch (ValidateException e) {
            path = "controller?action=show_course_form";
            req.setAttribute("message", e.getMessage());
        }catch (RuntimeException e){
            path = "controller?action=show_course_form";
            req.setAttribute("message", "ooops");
        }
        return path;
    }
}

