package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.actions.ActionUtils.setAllTeachers;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
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
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        setAllTeachers(req, generalService);
        return TEACHERS_PAGE;
    }
}
