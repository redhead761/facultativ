package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.EDIT_TEACHER_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_TEACHER_PROFILE_PAGE;

public class EditTeacherAction implements Action {
    TeacherService teacherService;

    public EditTeacherAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        return EDIT_TEACHER_PROFILE_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        TeacherDTO teacherDTO = getTeacherForAttribute(req);
        try {
            teacherDTO = teacherService.updateTeacher(teacherDTO);
            req.getSession().setAttribute(USER, teacherDTO);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(EDIT_TEACHER_ACTION);
    }

    private TeacherDTO getTeacherForAttribute(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        String degree = req.getParameter(DEGREE);
        return TeacherDTO.builder()
                .login(login)
                .name(name)
                .surname(surname)
                .email(email)
                .degree(degree)
                .build();
    }

}
