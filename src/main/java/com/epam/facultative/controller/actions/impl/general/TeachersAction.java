package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.utils.query_builders.ParamBuilderForQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.testSetUp;
import static com.epam.facultative.controller.actions.PageNameConstants.TEACHERS_PAGE;
import static com.epam.facultative.utils.query_builders.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

public class TeachersAction implements Action {
    private final AdminService adminService;

    public TeachersAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        ParamBuilderForQuery paramBuilder = teacherParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = adminService.getAllTeachersPagination(paramBuilder.getParam());
        req.setAttribute(TEACHERS, teachersWithRows.getValue());
        testSetUp(req, teachersWithRows.getKey());
        return TEACHERS_PAGE;
    }
}
