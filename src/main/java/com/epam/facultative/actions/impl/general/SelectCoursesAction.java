package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.entity.Role;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.*;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SelectCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        System.out.println("In select action");
        req.getSession().removeAttribute("sort_type");
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int recordsPerPage = 5;

        String path = null;
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        List<CourseDTO> courses = null;
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        String selectType = req.getParameter("select_type");
        if (selectType == null) {
            selectType = (String) req.getSession().getAttribute("select_type");
        }
        req.getSession().setAttribute("select_type", selectType);
        switch (selectType) {
            case "by_teacher" -> {
                int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
                courses = generalService.getCoursesByTeacher(teacherId, (page - 1) * recordsPerPage, recordsPerPage);
                int noOfRecords = generalService.getNoOfRecords();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                req.getSession().setAttribute("noOfCoursesPages", noOfPages);
                req.getSession().setAttribute("currentPage", page);
            }
            case "by_category" -> {
                int categoryId = Integer.parseInt(req.getParameter("category_id"));
                courses = generalService.getCoursesByCategory(categoryId, (page - 1) * recordsPerPage, recordsPerPage);
                int noOfRecords = generalService.getNoOfRecords();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                req.getSession().setAttribute("noOfCoursesPages", noOfPages);
                req.getSession().setAttribute("currentPage", page);

            }
        }
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
