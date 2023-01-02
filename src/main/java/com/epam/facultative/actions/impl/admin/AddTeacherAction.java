package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.entities.Teacher;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.PageNameConstants.ADD_TEACHER_PAGE;

public class AddTeacherAction implements Action {
    private final AdminService adminService;

    public AddTeacherAction() {
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        Teacher newUser = getTeacherForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            adminService.addTeacher(newUser);
            req.getSession().setAttribute("message", "Successful");
        } catch (ValidateException e) {
            req.getSession().setAttribute("message", e.getMessage());
        }
        return ADD_TEACHER_PAGE;
    }

    private Teacher getTeacherForAttribute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String degree = req.getParameter("degree");
        return Teacher.builder()
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
            throw new ValidateException("Wrong repeat password");
        }
    }
}
