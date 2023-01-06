package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class RegisterAction implements Action {
    private final StudentService studentService;

    public RegisterAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String path;
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        Student newUser = getStudentForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            studentService.addStudent(newUser);
            path = REGISTER_PAGE;
            req.getSession().setAttribute("message", "Successful");
        } catch (ValidateException e) {
            path = REGISTER_PAGE;
            req.getSession().setAttribute("message", e.getMessage());
        }
        return path;
    }

    private Student getStudentForAttribute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        return Student.builder()
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .build();
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException("Wrong repeat password");
        }
    }
}
