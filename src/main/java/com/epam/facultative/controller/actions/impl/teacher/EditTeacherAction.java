package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.EDIT_STUDENT_ACTION;
import static com.epam.facultative.controller.actions.ActionNameConstants.EDIT_TEACHER_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.EDIT_STUDENT_PROFILE_PAGE;
import static com.epam.facultative.controller.actions.PageNameConstants.EDIT_TEACHER_PROFILE_PAGE;

public class EditTeacherAction implements Action {
    TeacherService teacherService;

    public EditTeacherAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        return EDIT_TEACHER_PROFILE_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        TeacherDTO teacherDTO = getTeacherForAttribute(req);
        try {
            teacherService.updateTeacher(teacherDTO);
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
