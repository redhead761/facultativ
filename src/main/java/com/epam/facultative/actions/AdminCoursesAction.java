package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AdminCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            List<CourseDTO> courses = generalService.sortCoursesByAlphabet();
            List<UserDTO> teachers = adminService.getAllTeachers();
            List<Category> categories = adminService.getAllCategories();
            req.setAttribute("courses", courses);
            req.setAttribute("teachers", teachers);
            req.setAttribute("categories", categories);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_courses.jsp";
    }
}
