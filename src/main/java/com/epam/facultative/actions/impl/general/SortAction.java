package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Role;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;
import static com.epam.facultative.entity.Role.*;

public class SortAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        System.out.println("In sort action");
        req.getSession().removeAttribute("select_type");
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int recordsPerPage = 5;
        System.out.println(page);

        String path = null;
        String sortType = null;
        if ((sortType = req.getParameter("sort_type")) == null) {
            sortType = (String) req.getSession().getAttribute("sort_type");
        }

        String typeCabinet = req.getParameter("cabinet_type");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        List<CourseDTO> courses = null;
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        req.getSession().setAttribute("sort_type", sortType);
        switch (sortType) {
            case "alphabet" ->
                    courses = generalService.sortCoursesByAlphabet((page - 1) * recordsPerPage, recordsPerPage);
            case "reverse alphabet" ->
                    courses = generalService.sortCoursesByAlphabetReverse((page - 1) * recordsPerPage, recordsPerPage);
            case "duration" ->
                    courses = generalService.sortCoursesByDuration((page - 1) * recordsPerPage, recordsPerPage);
            case "amount students" ->
                    courses = generalService.sortCoursesBuAmountOfStudents((page - 1) * recordsPerPage, recordsPerPage);
        }
        for (var course : courses
        ) {System.out.println(course.getTitle() + "=" + course.getDuration());

        }
        int noOfRecords = generalService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCoursesPages", noOfPages);
        req.getSession().setAttribute("currentPage", page);

        Role role = (Role) req.getSession().getAttribute("role");
        switch (role) {
            case ADMIN -> path = ADMIN_PAGE;
            case STUDENT -> path = STUDENT_PAGE;
            case TEACHER -> path = TEACHER_PAGE;
        }
        req.getSession().setAttribute("courses", courses);
        return path;
    }
}
