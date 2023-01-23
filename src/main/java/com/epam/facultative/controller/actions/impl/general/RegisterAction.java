package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.REGISTER_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class RegisterAction implements Action {
    private final StudentService studentService;

    public RegisterAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, STUDENT);
        return REGISTER_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String password = req.getParameter(PASSWORD);
        String repeatPassword = req.getParameter(REPEAT_PASSWORD);
        StudentDTO student = getStudentForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            studentService.addStudent(student);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(STUDENT, student);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(REGISTER_ACTION);
    }

    private StudentDTO getStudentForAttribute(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        int courseNumber = Integer.parseInt(req.getParameter(COURSE_NUMBER));
        return StudentDTO.builder()
                .id(0)
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .courseNumber(courseNumber)
                .build();
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException(WRONG_REPEAT_PASSWORD);
        }
    }
}
