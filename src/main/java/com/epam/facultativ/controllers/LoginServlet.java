package com.epam.facultativ.controllers;

import com.epam.facultativ.entity.Course;
import com.epam.facultativ.entity.User;
import com.epam.facultativ.service.CourseService;
import com.epam.facultativ.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = new UserService().getUserByLogin(login);
        List<Course> courses = new CourseService().sortByAZ();
        if (user != null && user.getPassword().equals(req.getParameter("password"))) {
            req.getSession().setAttribute("user", user);
            req.setAttribute("courses", courses);
            switch (user.getRole().getTitle()) {
                case "ADMIN" -> req.getRequestDispatcher("AdminCabinet.jsp").forward(req, resp);
                case "TEACHER" -> req.getRequestDispatcher("TeacherCabinet.jsp").forward(req, resp);
                case "STUDENT" -> req.getRequestDispatcher("StudentCabinet.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("errPage.jsp");
        }
    }
}
