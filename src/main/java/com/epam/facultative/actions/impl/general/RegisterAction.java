package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;

public class RegisterAction implements Action {
    private final StudentService studentService;
    private final AdminService adminService;

    public RegisterAction() {
        studentService = ServiceFactory.getInstance().getStudentService();
        adminService = ServiceFactory.getInstance().getAdminService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String path = null;
        String type = req.getParameter("type");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        User newUser = getUserForAttribute(req);
        if (type.equals("student")) {
            try {
                checkConfirmPassword(password, repeatPassword);
                studentService.addStudent(newUser);
                path = AUTH_PAGE;
                req.getSession().setAttribute("message", "Successful");
            } catch (ValidateException e) {
                path = REGISTER_PAGE;
                req.getSession().setAttribute("message", e.getMessage());
            }
        }
        if (type.equals("teacher")) {
            try {
                checkConfirmPassword(password, repeatPassword);
                adminService.addTeacher(newUser);
                path = ADD_TEACHER_PAGE;
                req.setAttribute("message", "Successful");
            } catch (ValidateException e) {
                path = ADD_TEACHER_PAGE;
                req.setAttribute("message", e.getMessage());
            }
        }
        return path;
    }

    private User getUserForAttribute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        return new User(login, password, name, surname, email);
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException("Wrong repeat password");
        }
    }
}
