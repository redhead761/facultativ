package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;

public class RegisterAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        String type = req.getParameter("type");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        User newUser = new User(login, password, name, surname, email);
        if (type.equals("student")) {
            StudentService studentService = ServiceFactory.getInstance().getStudentService();
            try {
                if (!password.equals(repeatPassword)) {
                    throw new ValidateException("Wrong repeat password");
                }
                GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
                req.getSession().setAttribute("courses", generalService.getAllCourses());
                studentService.addStudent(newUser);
                UserDTO user = generalService.authorization(login, password);
                req.getSession().setAttribute("user", user);
                path = STUDENT_PAGE;
            } catch (ServiceException e) {
                path = ERROR_PAGE;
                req.setAttribute("message", e.getMessage());
            } catch (ValidateException e) {
                req.setAttribute("login", login);
                req.setAttribute("name", name);
                req.setAttribute("surname", surname);
                req.setAttribute("email", email);
                path = REGISTER_PAGE;
                req.setAttribute("message", e.getMessage());
            }
        }
        if (type.equals("teacher")) {
            AdminService adminService = ServiceFactory.getInstance().getAdminService();
            try {
                if (!password.equals(repeatPassword)) {
                    throw new ValidateException("Wrong repeat password");
                }
                adminService.addTeacher(newUser);
                path = ADD_TEACHER_PAGE;
                req.setAttribute("message", "Successful");
            } catch (ServiceException e) {
                path = ERROR_PAGE;
                req.setAttribute("message", e.getMessage());
            } catch (ValidateException e) {
                req.setAttribute("login", login);
                req.setAttribute("name", name);
                req.setAttribute("surname", surname);
                req.setAttribute("email", email);
                path = ADD_TEACHER_PAGE;
                req.setAttribute("message", e.getMessage());
            }
        }
        return path;
    }
}
