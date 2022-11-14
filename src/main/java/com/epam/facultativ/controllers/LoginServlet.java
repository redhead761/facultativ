package com.epam.facultativ.controllers;


import com.epam.facultativ.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");

        String user = new UserService().getUserByLogin(login).getLogin();

        resp.getWriter()
                .append("<html>")
                .append("<body>")
                .append("<h2>")
                .append("Hello ")
                .append(user)
                .append("</h2")
                .append("/body>")
                .append("</html>");
    }
}
