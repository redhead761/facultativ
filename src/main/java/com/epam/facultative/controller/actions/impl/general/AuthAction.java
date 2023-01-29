package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.UserDTO;
import com.epam.facultative.model.entities.Status;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.recaptcha.Recaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.AUTH_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;


public class AuthAction implements Action {
    private final GeneralService generalService;
    private final Recaptcha recaptcha;

    public AuthAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        recaptcha = appContext.getRecaptcha();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, LOGIN);
        return AUTH_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String path = null;
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String gRecaptchaResponse = req.getParameter(RECAPTCHA);
        try {
            boolean verify = recaptcha.verify(gRecaptchaResponse);
            if (!verify) {
                throw new ValidateException(MISSED_CAPTCHA);
            }
            UserDTO user = generalService.authorization(login, password);
            req.getSession().setAttribute(USER, user);
            req.getSession().setAttribute(ROLE, user.getRole());
            req.getSession().setAttribute(STATUSES, Status.values());
            switch (user.getRole()) {
                case ADMIN -> path = ADMIN_PROFILE_PAGE;
                case TEACHER -> path = TEACHER_PROFILE_PAGE;
                case STUDENT -> path = STUDENT_PROFILE_PAGE;
            }
        } catch (ValidateException e) {
            req.getSession().setAttribute(LOGIN, login);
            req.getSession().setAttribute(ERROR, e.getMessage());
            return getGetAction(AUTH_ACTION);
        }
        return path;
    }
}
