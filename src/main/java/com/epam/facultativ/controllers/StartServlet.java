package com.epam.facultativ.controllers;

import com.epam.facultativ.entity.Course;
import com.epam.facultativ.service.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action;
        List<Course> courses = new CourseService().sortByAZ();

        if (req.getParameter("action") == null) {
            courses = new CourseService().sortByAZ();
        } else {
            action = req.getParameter("action");
            switch (action) {
                case "sortaz" -> courses = new CourseService().sortByAZ();
                case "sortza" -> courses = new CourseService().sortByZA();
                case "sortduration" -> courses = new CourseService().sortByDuration();
                case "sortstudents" -> courses = new CourseService().sortByNumber();
            }
        }
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
