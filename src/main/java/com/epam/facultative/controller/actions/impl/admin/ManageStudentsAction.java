package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.utils.param_builders.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.testSetUp;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.actions.PageNameConstants.*;
import static com.epam.facultative.utils.param_builders.ParamBuilderForQueryUtil.studentParamBuilderForQuery;

public class ManageStudentsAction implements Action {
    private final AdminService adminService;

    public ManageStudentsAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE);
        ParamBuilderForQuery paramBuilder = studentParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<StudentDTO>> studentsWithRows = adminService.getAllStudentsPagination(paramBuilder.getParam());
        req.setAttribute(STUDENTS, studentsWithRows.getValue());
        testSetUp(req, studentsWithRows.getKey());
        return MANAGE_STUDENTS_PAGE;
    }
}
