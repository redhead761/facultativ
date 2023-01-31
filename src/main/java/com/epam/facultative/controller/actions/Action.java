package com.epam.facultative.controller.actions;

import com.epam.facultative.model.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Action interface. Implement it to create new actions
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface Action {

    /**
     * Obtains path to sendRedirect or forward in front-controller. Edits request and response if needed.
     *
     * @param req  passed by controller
     * @param resp passed by controller
     * @return path to return to front-controller
     * @throws ServiceException - any unhandled exception. Will cause front-controller to redirect to error page
     * @throws IOException      - any unhandled exception. Will cause front-controller to redirect to error page
     * @throws ServletException - any unhandled exception. Will cause front-controller to redirect to error page
     */
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException, ServletException;
}
