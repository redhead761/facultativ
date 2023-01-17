package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.utils.recaptcha.Recaptcha;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;


public class AuthAction implements Action {
    private final GeneralService generalService;
    private final Recaptcha recaptcha;

    public AuthAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        recaptcha = appContext.getRecaptcha();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
            System.out.println(gRecaptchaResponse);
            boolean verify = recaptcha.verify(gRecaptchaResponse);
            if (!verify) {
                throw new ValidateException("You missed the Captcha.");
            }
            UserDTO user = generalService.authorization(login, password);
            req.getSession().setAttribute(USER, user);
            req.getSession().setAttribute(ROLE, user.getRole());
            req.getSession().setAttribute("statuses", Status.values());
            switch (user.getRole()) {
                case ADMIN -> path = ADMIN_PROFILE_PAGE;
                case TEACHER -> path = TEACHER_PROFILE_PAGE;
                case STUDENT -> path = STUDENT_PROFILE_PAGE;
            }
        } catch (ValidateException e) {
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute(MESSAGE, e.getMessage());
            return AUTH_PAGE;
        }
        return path;
    }
}
