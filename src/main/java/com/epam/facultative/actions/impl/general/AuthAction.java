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

import static com.epam.facultative.actions.PageNameConstants.*;


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
                case ADMIN -> path = ADMIN_PROFILE_PAGE;
                case TEACHER -> path = TEACHER_PROFILE_PAGE;
                case STUDENT -> path = STUDENT_PROFILE_PAGE;
            }
        } catch (ValidateException e) {
            req.setAttribute("login", login);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(AUTH_PAGE).forward(req, resp);
        }
        return path;
    }
}
