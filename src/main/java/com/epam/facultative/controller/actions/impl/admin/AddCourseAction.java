package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.facultative.controller.actions.ActionNameConstants.*;
import static com.epam.facultative.controller.AttributeConstants.*;

public class AddCourseAction implements Action {

    private final AdminService adminService;
    public AddCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        CourseDTO course = null;
        try {
            course = getCourseFromParameter(req);
            adminService.addCourse(course);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(COURSE, course);
            req.getSession().setAttribute(MESSAGE, e.getMessage());
        }
        return CONTROLLER + SHOW_ADD_COURSE_ACTION;
    }

    private CourseDTO getCourseFromParameter(HttpServletRequest req) throws ServiceException, ValidateException {
        String title = req.getParameter("title");
        int duration = Integer.parseInt(req.getParameter("duration"));
        LocalDate date = LocalDate.parse(req.getParameter("start_date"));
        String description = req.getParameter("description");
        Status status = Status.valueOf(req.getParameter("status"));
        int categoryId = Integer.parseInt(req.getParameter("category"));
        CategoryDTO categoryDTO = adminService.getCategory(categoryId);
        return CourseDTO.builder()
                .id(0)
                .title(title)
                .duration(duration)
                .startDate(date)
                .description(description)
                .category(categoryDTO)
                .status(status)
                .build();
    }
}

