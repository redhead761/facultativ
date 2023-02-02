package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.RECOVERY_PASSWORD_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.RECOVERY_PASSWORD_PAGE;

/**
 * Accessible by general. Allows to recovery password. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class RecoveryPasswordAction implements Action {
    GeneralService generalService;
    AppContext appContext;

    public RecoveryPasswordAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        this.appContext = appContext;
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
     * @return recovery password page after trying to recovery password
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, EMAIL);
        return RECOVERY_PASSWORD_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to recovery password.Return input and error message
     * in case if not able.
     *
     * @param req to get users id and set message in case of successful recovery password
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String email = req.getParameter(EMAIL);
        try {
            generalService.recoveryPassword(email, appContext);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL_RECOVERY);
        } catch (ValidateException e) {
            req.getSession().setAttribute(EMAIL, email);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(RECOVERY_PASSWORD_ACTION);
    }
}

