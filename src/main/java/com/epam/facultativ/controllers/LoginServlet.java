package com.epam.facultativ.controllers;

import com.epam.facultativ.entity.User;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = new UserService().getUserByLogin(login);
        if (user != null && user.getPassword().equals(req.getParameter("password"))) {
            req.getSession().setAttribute("user", user);
            switch (user.getRole().getTitle()) {
                case "ADMIN" -> resp.sendRedirect("AdminCabinet.jsp");
                case "TEACHER" -> resp.sendRedirect("TeacherCabinet.jsp");
                case "STUDENT" -> resp.sendRedirect("StudentCabinet.jsp");
            }
        } else {
            resp.sendRedirect("errPage.jsp");
        }
    }


}
