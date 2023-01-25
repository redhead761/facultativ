package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.RECOVERY_PASSWORD_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.RECOVERY_PASSWORD_PAGE;

public class RecoveryPasswordAction implements Action {
    GeneralService generalService;

    public RecoveryPasswordAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, EMAIL);
        return RECOVERY_PASSWORD_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        String email = req.getParameter(EMAIL);
        try {
            generalService.recoveryPassword(email);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(EMAIL, email);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(RECOVERY_PASSWORD_ACTION);
    }
}

