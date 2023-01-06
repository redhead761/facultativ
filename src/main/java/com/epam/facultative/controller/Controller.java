package com.epam.facultative.controller;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionFactory;
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
        System.out.println("In DOGET");
        req.getRequestDispatcher(process(req, resp)).forward(req, resp);
        System.out.println("Out DOGET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In DOPOST");
        resp.sendRedirect(process(req, resp));
        System.out.println("Out DOPOST");
    }

    private String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In PROCESS");
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
