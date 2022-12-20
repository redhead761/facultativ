package com.epam.facultative.controller;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionFactory;
import com.epam.facultative.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(process(req, resp));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(process(req, resp));
    }

    private String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getParameter("action");
        Action action = ACTION_FACTORY.getAction(actionName);
        try {
            return action.execute(req, resp);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return "error.jsp";
        }
    }
}
