package com.epam.facultative.actions;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class AuthAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            UserDTO user = generalService.authorization(login, password);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("statuses", Status.values());
            req.setAttribute("courses", generalService.getAllCourses());
            req.setAttribute("categories", generalService.getAllCategories());
            req.setAttribute("teachers", generalService.getAllTeachers());
            switch (user.getRole()) {
                case ADMIN -> path = ADMIN_PAGE;
                case TEACHER -> path = TEACHER_PAGE;
                case STUDENT -> path = STUDENT_PAGE;
            }
        } catch (ValidateException e) {
            path = AUTH_PAGE;
            req.setAttribute("error", e.getMessage());
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
