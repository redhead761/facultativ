package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AuthAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            UserDTO user = generalService.authorization(login, password);
            List<CourseDTO> courses = generalService.sortByAlphabet();
;
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("courses", courses);

            switch (user.getRole()) {
                case ADMIN -> path = "admin.jsp";
                case TEACHER -> path = "teacher.jsp";
                case STUDENT -> path = "student.jsp";
            }
        } catch (ValidateException e) {
            path = "auth.jsp";
            req.setAttribute("error", e.getMessage());
        } catch (ServiceException e) {
            path = "error.jsp";
            req.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
