package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.CHANGE_PASSWORD_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.CHANGE_PASSWORD_PAGE;

/**
 * Accessible by general. Allows to change user password in database. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ChangePasswordAction implements Action {
    GeneralService generalService;

    public ChangePasswordAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
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
     * @return change password page after trying to change password
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, MESSAGE, ERROR);
        return CHANGE_PASSWORD_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to change password.Return error message in case if not able.
     *
     * @param req to get users id and set message in case of successful change password
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String oldPassword = req.getParameter(OLD_PASSWORD);
        String newPassword = req.getParameter(NEW_PASSWORD);
        String repeatPassword = req.getParameter(REPEAT_PASSWORD);
        String userId = req.getParameter(USER_ID);
        try {
            checkConfirmPassword(newPassword, repeatPassword);
            generalService.changePassword(oldPassword, newPassword, Integer.parseInt(userId));
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(CHANGE_PASSWORD_ACTION);
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException(WRONG_REPEAT_PASSWORD);
        }
    }
}
