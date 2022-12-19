package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.entity.Role;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SelectCoursesAction implements Action {
    private final GeneralService generalService;

    public SelectCoursesAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        removeRedundantAttribute(req);
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        String path = null;
        List<CourseDTO> courses = null;


        String selectType = req.getParameter("select_type");
        if (selectType == null) {
            selectType = (String) req.getSession().getAttribute("select_type");
        }
        req.getSession().setAttribute("select_type", selectType);
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        switch (selectType) {
            case "by_teacher" -> {
                int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
                courses = generalService.getCoursesByTeacher(teacherId, (page - 1) * recordsPerPage, recordsPerPage);
            }
            case "by_category" -> {
                int categoryId = Integer.parseInt(req.getParameter("category_id"));
                courses = generalService.getCoursesByCategory(categoryId, (page - 1) * recordsPerPage, recordsPerPage);
            }

        }
        req.getSession().setAttribute("courses", courses);
        int noOfRecords = generalService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCoursesPages", noOfPages);
        req.getSession().setAttribute("currentPage", page);

        Role role = (Role) req.getSession().getAttribute("role");
        switch (role) {
            case ADMIN -> path = ADMIN_PAGE;
            case STUDENT -> path = STUDENT_PAGE;
            case TEACHER -> path = TEACHER_PAGE;
        }
        return path;
    }

    private void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("sort_type");
    }
}
