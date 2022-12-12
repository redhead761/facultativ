package com.epam.facultative.controller;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In do get");
        req.getRequestDispatcher(process(req)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In do post");
        resp.sendRedirect(process(req));
    }

    private String process(HttpServletRequest req) {
        System.out.println("In process");
        String actionName = req.getParameter("action");
        Action action = ACTION_FACTORY.getAction(actionName);
        return action.execute(req);
    }
}
