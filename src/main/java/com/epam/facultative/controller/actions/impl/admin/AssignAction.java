package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.ActionNameConstants.ASSIGN_ACTION;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.ASSIGN_PAGE;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

public class AssignAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;

    public AssignAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);

    }

    private String executeGet(HttpServletRequest req) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        req.setAttribute(COURSE_ID, courseId);
        ParamBuilderForQuery paramBuilderForQuery = teacherParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = generalService.getTeachers(paramBuilderForQuery.getParam());
        req.setAttribute(TEACHERS, teachersWithRows.getValue());
        testSetUp(req, teachersWithRows.getKey());
        transferAttributeFromSessionToRequest(req, MESSAGE);
        return ASSIGN_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String courseId = req.getParameter(COURSE_ID);
        String teacherId = req.getParameter(TEACHER_ID);
        try {
            adminService.assigned(Integer.parseInt(courseId), Integer.parseInt(teacherId));
        } catch (ValidateException e) {
            throw new ServiceException(e);
        }
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        return getGetAction(ASSIGN_ACTION, COURSE_ID, courseId);
    }
}
