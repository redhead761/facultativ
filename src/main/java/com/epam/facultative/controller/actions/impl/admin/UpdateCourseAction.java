package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class UpdateCourseAction implements Action {
    private final AdminService adminService;

    public UpdateCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        try {
            CourseDTO course = getCourseFromParameter(req);
            adminService.updateCourse(course);
            if (req.getParameter("teacher") != null) {
                int courseId = Integer.parseInt(req.getParameter("course_id"));
                int teacherId = Integer.parseInt(req.getParameter("teacher"));
                adminService.assigned(courseId, teacherId);
            }
            req.getSession().setAttribute("message", "Changes saved");
        } catch (ValidateException e) {
            req.getSession().setAttribute("message", e.getMessage());
            return EDIT_COURSE_PAGE;
        }
        return EDIT_COURSE_PAGE;
    }

    private CourseDTO getCourseFromParameter(HttpServletRequest req) throws ServiceException, ValidateException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");
        Status status = Status.valueOf(req.getParameter("status"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        CategoryDTO categoryDTO = adminService.getCategory(categoryId);
        return CourseDTO.builder()
                .id(courseId)
                .title(title)
                .duration(duration)
                .startDate(date)
                .description(description)
                .category(categoryDTO)
                .status(status)
                .build();
    }
}

