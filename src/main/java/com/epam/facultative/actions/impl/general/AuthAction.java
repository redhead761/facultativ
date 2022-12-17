package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.*;
import com.epam.facultative.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;

public class AuthAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        int page = 1;
        int recordsPerPage = 5;


        System.out.println("In auth action");
        String path = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            UserDTO user = generalService.authorization(login, password);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("role", user.getRole());
            req.getSession().setAttribute("statuses", Status.values());
            req.getSession().setAttribute("courses", generalService.getAllCourses(0, recordsPerPage));

            int noOfRecords = generalService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.getSession().setAttribute("noOfCoursesPages", noOfPages);
            req.getSession().setAttribute("currentPage", page);

            req.getSession().setAttribute("categories", generalService.getAllCategories());
            req.getSession().setAttribute("teachers", generalService.getAllTeachers());
            switch (user.getRole()) {
                case ADMIN -> path = ADMIN_PAGE;
                case TEACHER -> path = TEACHER_PAGE;
                case STUDENT -> path = STUDENT_PAGE;
            }
        } catch (ValidateException e) {
            req.setAttribute("login", login);
            path = AUTH_PAGE;
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(path).forward(req, resp);
        }
        return path;
    }
}
