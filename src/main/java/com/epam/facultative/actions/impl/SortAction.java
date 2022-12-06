package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.facultative.actions.impl.Constants.*;

public class SortAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        String typeSort = req.getParameter("sort_type");
        String typeCabinet = req.getParameter("cabinet_type");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        List<CourseDTO> courses = null;
        try {
            req.setAttribute("teachers", generalService.getAllTeachers());
            req.setAttribute("categories", generalService.getAllCategories());
            switch (typeSort) {
                case "alphabet" -> courses = generalService.sortCoursesByAlphabet();
                case "reverse alphabet" -> courses = generalService.sortCoursesByAlphabetReverse();
                case "duration" -> courses = generalService.sortCoursesByDuration();
                case "amount students" -> courses = generalService.sortCoursesBuAmountOfStudents();
            }
            switch (typeCabinet) {
                case "admin" -> path = ADMIN_PAGE;
                case "student" -> path = STUDENT_PAGE;
                case "teacher" -> path = TEACHER_PAGE;
            }
            req.setAttribute("courses", courses);
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
