package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;


public class AuthAction implements Action {
    private final GeneralService generalService;

    public AuthAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            UserDTO user = generalService.authorization(login, password);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("role", user.getRole());
            req.getSession().setAttribute("statuses", Status.values());
            switch (user.getRole()) {
                case ADMIN -> path = MANAGE_COURSES_ACTION;
                case TEACHER -> path = ALL_COURSES_ACTION;
                case STUDENT -> path = SHOW_STUDENT_CABINET_ACTION;
            }
        } catch (ValidateException e) {
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("message", e.getMessage());
            path = AUTH_PAGE;
        }
        return path;
    }
}
