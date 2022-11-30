package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SelectByCategoryAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            List<UserDTO> teachers = generalService.getAllTeachers();
            List<Category> categories = generalService.getAllCategories();
            req.setAttribute("teachers", teachers);
            req.setAttribute("categories", categories);
            String categoryId = req.getParameter("category_id");
            List<CourseDTO> courses = generalService.getCoursesByCategory(Integer.parseInt(categoryId));
            req.setAttribute("courses", courses);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return ADMIN_PAGE;
    }
}
