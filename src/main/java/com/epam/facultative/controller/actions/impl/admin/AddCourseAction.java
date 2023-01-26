package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.entities.Status;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.facultative.controller.constants.ActionNameConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.ADD_COURSE_PAGE;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.categoryParamBuilderForQuery;

public class AddCourseAction implements Action {

    private final AdminService adminService;
    private final GeneralService generalService;

    public AddCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);

    }

    private String executeGet(HttpServletRequest req) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, COURSE);
        req.setAttribute(CATEGORIES, generalService.getCategories(categoryParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam()).getValue());
        return ADD_COURSE_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        CourseDTO course = null;
        try {
            course = getCourseFromParameter(req);
            adminService.addCourse(course);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(COURSE, course);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ADD_COURSE_ACTION);
    }

    private CourseDTO getCourseFromParameter(HttpServletRequest req) throws ServiceException, ValidateException {
        String title = req.getParameter(TITLE);
        int duration = Integer.parseInt(req.getParameter(DURATION));
        LocalDate date = LocalDate.parse(req.getParameter(START_DATE));
        String description = req.getParameter(DESCRIPTION);
        Status status = Status.valueOf(req.getParameter(STATUS));
        String categoryId = req.getParameter(CATEGORY);
        CategoryDTO categoryDTO = adminService.getCategory(Integer.parseInt(categoryId));
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

