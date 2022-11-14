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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");

        User user = new UserService().getUserByLogin(login);


        switch (user.getRole().getTitle()) {
            case "ADMIN" -> {
                req.setAttribute("res", user.getFirstName());
                req.getRequestDispatcher("AdminCabinet.jsp").forward(req, resp);
                break;
            }
            case "TEACHER" -> {
                req.setAttribute("res", user.toString());
                req.getRequestDispatcher("TeacherCabinet.jsp").forward(req, resp);
                break;
            }
            case "STUDENT" -> {
                req.setAttribute("res", user.toString());
                req.getRequestDispatcher("StudentCabinet.jsp.jsp").forward(req, resp);
                break;
            }
        }



//        resp.getWriter()
//                .append("<html>")
//                .append("<body>")
//                .append("<h2>")
//                .append("Hello ")
//                .append(user)
//                .append("</h2")
//                .append("/body>")
//                .append("</html>");
    }
}
