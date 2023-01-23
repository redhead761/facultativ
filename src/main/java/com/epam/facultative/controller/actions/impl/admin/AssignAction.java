package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.utils.param_builders.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.actions.ActionNameConstants.ASSIGN_ACTION;
import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.ASSIGN_PAGE;
import static com.epam.facultative.utils.param_builders.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

public class AssignAction implements Action {
    private final AdminService adminService;

    public AssignAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);

    }

    private String executeGet(HttpServletRequest req) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        req.setAttribute(COURSE_ID, courseId);
        ParamBuilderForQuery paramBuilderForQuery = teacherParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = adminService.getAllTeachersPagination(paramBuilderForQuery.getParam());
        req.setAttribute(TEACHERS, teachersWithRows.getValue());
        testSetUp(req, teachersWithRows.getKey());
        transferAttributeFromSessionToRequest(req, MESSAGE);
        return ASSIGN_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        int teacherId = Integer.parseInt(req.getParameter(TEACHER_ID));
        try {
            adminService.assigned(courseId, teacherId);
        } catch (ValidateException e) {
            throw new ServiceException(e);
        }
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        return getGetAction(ASSIGN_ACTION, COURSE_ID, String.valueOf(courseId));
    }
}
