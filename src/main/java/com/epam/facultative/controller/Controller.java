package com.epam.facultative.controller;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionFactory;
import com.epam.facultative.model.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller  class. Implements Front-controller pattern. Chooses action to execute and redirect or forward result.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100)
public class Controller extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    /**
     * Calls and executes action and then forwards requestDispatcher
     *
     * @param req  comes from user
     * @param resp comes from user
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(process(req, resp)).forward(req, resp);
    }

    /**
     * Calls and executes action and then sendRedirect for PRG pattern implementation
     *
     * @param req  comes from user
     * @param resp comes from user
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(process(req, resp));
    }

    /**
     * Obtains path to use in doPost/doGet methods. In case of error will return error page
     *
     * @return path
     */
    private String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getParameter("action");
        Action action = ACTION_FACTORY.getAction(actionName);
        try {
            return action.execute(req, resp);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            req.getSession().invalidate();
            return "error.jsp";
        }
    }
}
