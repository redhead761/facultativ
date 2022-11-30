package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SelectCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
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
            req.setAttribute("courses", courses);
            path = ADMIN_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
