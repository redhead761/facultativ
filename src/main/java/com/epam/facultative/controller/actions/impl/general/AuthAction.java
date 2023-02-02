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

/**
 * Accessible by general. Allows to authorization. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class AuthAction implements Action {
    private final GeneralService generalService;
    private final Recaptcha recaptcha;

    public AuthAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        recaptcha = appContext.getRecaptcha();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param req to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param req to get message attribute from session and put it in request
     * @return auth page after trying to authorization
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, LOGIN, MESSAGE);
        return AUTH_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to authorization.Return input and error message
     * in case if not able.
     *
     * @param req to get users id and set message in case of successful authorized
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String path = null;
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
//        String gRecaptchaResponse = req.getParameter(RECAPTCHA);
        try {
//            boolean verify = recaptcha.verify(gRecaptchaResponse);
//            if (!verify) {
//                throw new ValidateException(MISSED_CAPTCHA);
//            }
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
