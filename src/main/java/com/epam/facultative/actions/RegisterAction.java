package com.epam.facultative.actions;

import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

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
        User user = new User(login, password, name, surname, email);
        if (type.equals("student")) {
            StudentService studentService = ServiceFactory.getInstance().getStudentService();
            try {
                try {
                    if (!password.equals(repeatPassword)) {
                        throw new ValidateException("Wrong repeat password");
                    }
                    GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
                    req.setAttribute("courses",generalService.sortByAlphabet());
                    studentService.addStudent(user);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
                path = "student.jsp";
            } catch (ServiceException e) {
                path = "error.jsp";
                req.setAttribute("error", e.getMessage());
            } catch (ValidateException e) {
                path = "register.jsp";
                req.setAttribute("error", e.getMessage());
            }
        }
        if (type.equals("teacher")) {
            AdminService adminService = ServiceFactory.getInstance().getAdminService();
            try {
                try {
                    if (!password.equals(repeatPassword)) {
                        throw new ValidateException("Wrong repeat password");
                    }
                    adminService.addTeacher(user);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
                path = "add_teacher.jsp";
            } catch (ServiceException e) {
                path = "error.jsp";
                req.setAttribute("error", e.getMessage());
            } catch (ValidateException e) {
                path = "add_teacher.jsp";
                req.setAttribute("error", e.getMessage());
            }
        }
        return path;
    }
}
