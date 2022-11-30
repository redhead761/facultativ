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

import static com.epam.facultative.actions.Constants.*;

public class ManageCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            List<CourseDTO> courses = generalService.getAllCourses();
            List<UserDTO> teachers = generalService.getAllTeachers();
            List<Category> categories = generalService.getAllCategories();
            req.setAttribute("courses", courses);
            req.setAttribute("teachers", teachers);
            req.setAttribute("categories", categories);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return ADMIN_PAGE;
    }
}
