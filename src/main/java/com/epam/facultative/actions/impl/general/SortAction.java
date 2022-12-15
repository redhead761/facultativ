package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SortAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String path = null;
        String typeSort = req.getParameter("sort_type");
        String typeCabinet = req.getParameter("cabinet_type");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        List<CourseDTO> courses = null;
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
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
        req.getSession().setAttribute("courses", courses);
        return path;
    }
}
