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
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class RegisterAction implements Action {
    private final StudentService studentService;

    public RegisterAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        StudentDTO student = getStudentForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            studentService.addStudent(student);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(STUDENT, student);
            req.getSession().setAttribute(MESSAGE, e.getMessage());
        }
        return REGISTER_PAGE;
    }

    private StudentDTO getStudentForAttribute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        int courseNumber = Integer.parseInt(req.getParameter("course_number"));
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
            throw new ValidateException("Wrong repeat password");
        }
    }
}
