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
        resp.sendRedirect(process(req,resp));
//        req.getRequestDispatcher(process(req,resp)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In do post");
        resp.sendRedirect(process(req,resp));
        req.getSession().removeAttribute("action");
    }

    private String process(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In process");
        String actionName = req.getParameter("action");
        Action action = ACTION_FACTORY.getAction(actionName);
        return action.execute(req,resp);
    }
}
