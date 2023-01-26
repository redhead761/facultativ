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
import static com.epam.facultative.controller.actions.ActionUtils.testSetUp;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

public class ShowTeacherCoursesAction implements Action {
    private final GeneralService generalService;

    public ShowTeacherCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        TeacherDTO teacher = (TeacherDTO) req.getSession().getAttribute(USER);
        ParamBuilderForQuery paramBuilder = teacherParamBuilderForQuery()
                .setUserIdFilter(String.valueOf(teacher.getId()))
                .setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getCourses(paramBuilder.getParam());
        req.setAttribute(COURSES, coursesWithRows.getValue());
        testSetUp(req, coursesWithRows.getKey());
        return TEACHER_COURSES_PAGE;
    }
}
