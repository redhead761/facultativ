package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.facultative.controller.actions.ActionNameConstants.*;
import static com.epam.facultative.controller.AttributeConstants.*;

public class UpdateCourseAction implements Action {
    private final AdminService adminService;


    public UpdateCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String courseId = req.getParameter(COURSE_ID);
        try {
            CourseDTO course = getCourseFromParameter(req);
            if (req.getParameter(TEACHER_ID) != null && !req.getParameter(TEACHER_ID).isBlank()) {
                int teacherId = Integer.parseInt(req.getParameter(TEACHER_ID));
                adminService.assigned(Integer.parseInt(courseId), teacherId);
                course.setTeacher(adminService.getTeacher(teacherId));
            }
            adminService.updateCourse(course);
            if (req.getParameter("email_send") != null) {
                adminService.courseLaunchNewsLetter(course);
            }
            req.getSession().setAttribute(MESSAGE, CHANGES_SAVED);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return ActionUtils.getGetAction(SHOW_EDIT_COURSE_ACTION, COURSE_ID, courseId);
    }

    private CourseDTO getCourseFromParameter(HttpServletRequest req) throws ServiceException, ValidateException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        String title = req.getParameter(TITLE);
        int duration = Integer.parseInt(req.getParameter(DURATION));
        LocalDate date = LocalDate.parse(req.getParameter(START_DATE));
        String description = req.getParameter(DESCRIPTION);
        Status status = Status.valueOf(req.getParameter(STATUS));
        int categoryId = Integer.parseInt(req.getParameter(CATEGORY));
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

