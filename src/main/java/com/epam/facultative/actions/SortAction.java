package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SortAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        String typeSort = req.getParameter("sort_type");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        List<CourseDTO> courses = null;
        try {
            switch (typeSort) {
                case "alphabet" -> courses = generalService.sortByAlphabet();
                case "reverse alphabet" -> courses = generalService.sortByAlphabetReverse();
                case "duration" -> courses = generalService.sortByDuration();
                case "amount students" -> courses = generalService.sortByNumberStudentsOnCourse();
            }
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("courses", courses);
        switch (user.getRole()) {
            case ADMIN -> path = ADMIN_PAGE;
            case TEACHER -> path = TEACHER_PAGE;
            case STUDENT -> path = STUDENT_PAGE;
        }

        return path;
    }
}
