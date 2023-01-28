package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

public class ManageTeachersAction implements Action {
    private final GeneralService generalService;

    public ManageTeachersAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        setAllTeachers(req, generalService);
        return MANAGE_TEACHERS_PAGE;
    }
}
