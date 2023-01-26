package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.testSetUp;
import static com.epam.facultative.controller.constants.PageNameConstants.TEACHERS_PAGE;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

public class TeachersAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public TeachersAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        ParamBuilderForQuery paramBuilder = teacherParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = generalService.getTeachers(paramBuilder.getParam());
        req.setAttribute(TEACHERS, teachersWithRows.getValue());
        testSetUp(req, teachersWithRows.getKey());
        return TEACHERS_PAGE;
    }
}
