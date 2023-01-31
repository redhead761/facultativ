package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

/**
 * Accessible by teacher. Allows to get teacher`s courses list. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ShowTeacherCoursesAction implements Action {
    private final GeneralService generalService;

    public ShowTeacherCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    /**
     * Called from doGet method in front-controller. Get and set teacher courses
     *
     * @param req to get parameter and set courses
     * @return teacher courses page after trying to show teacher courses
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        TeacherDTO teacher = (TeacherDTO) req.getSession().getAttribute(USER);
        ParamBuilderForQuery paramBuilder = teacherParamBuilderForQuery()
                .setUserIdFilter(String.valueOf(teacher.getId()))
                .setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getCourses(paramBuilder.getParam());
        req.setAttribute(COURSES, coursesWithRows.getValue());
        setUpPaginate(req, coursesWithRows.getKey());
        return TEACHER_COURSES_PAGE;
    }
}
