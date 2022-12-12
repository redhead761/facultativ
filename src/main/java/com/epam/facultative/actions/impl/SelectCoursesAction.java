package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.epam.facultative.actions.impl.Constants.*;

public class SelectCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String path = null;
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        List<CourseDTO> courses = null;
        try {
            req.setAttribute("teachers", generalService.getAllTeachers());
            req.setAttribute("categories", generalService.getAllCategories());
            switch (req.getParameter("type")) {
                case "by_teacher" -> {
                    int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
                    courses = generalService.getCoursesByTeacher(teacherId);
                }
                case "by_category" -> {
                    int categoryId = Integer.parseInt(req.getParameter("category_id"));
                    courses = generalService.getCoursesByCategory(categoryId);
                }
            }
            switch (req.getParameter("cabinet_type")) {
                case "admin" -> path = ADMIN_PAGE;
                case "student" -> path = STUDENT_PAGE;
                case "teacher" -> path = TEACHER_PAGE;
            }
            req.setAttribute("courses", courses);
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
