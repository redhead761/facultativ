package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.ADD_TEACHER_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.ADD_TEACHER_PAGE;

public class AddTeacherAction implements Action {
    private final AdminService adminService;

    public AddTeacherAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, TEACHER);
        return ADD_TEACHER_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String password = req.getParameter(PASSWORD);
        String repeatPassword = req.getParameter(REPEAT_PASSWORD);
        TeacherDTO teacher = getTeacherForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            adminService.addTeacher(teacher);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(TEACHER, teacher);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ADD_TEACHER_ACTION);
    }

    private TeacherDTO getTeacherForAttribute(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        String degree = req.getParameter(DEGREE);
        return TeacherDTO.builder()
                .id(0)
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .degree(degree)
                .build();
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException(WRONG_REPEAT_PASSWORD);
        }
    }
}
