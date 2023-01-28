package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.studentParamBuilderForQuery;

public class ManageStudentsAction implements Action {
    private final AdminService adminService;

    public ManageStudentsAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE);
        ParamBuilderForQuery paramBuilder = studentParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<StudentDTO>> studentsWithRows = adminService.getStudents(paramBuilder.getParam());
        req.setAttribute(STUDENTS, studentsWithRows.getValue());
        setUpPaginate(req, studentsWithRows.getKey());
        return MANAGE_STUDENTS_PAGE;
    }
}
