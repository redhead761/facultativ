package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.Constants.*;


public class AuthAction implements Action {
    private final GeneralService generalService;

    public AuthAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String path = null;
//        int page = 1;
//        int recordsPerPage = 5;

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            UserDTO user = generalService.authorization(login, password);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("role", user.getRole());
            req.getSession().setAttribute("statuses", Status.values());

//            req.getSession().setAttribute("courses", generalService.getAllCourses(0, recordsPerPage));
//
//            int noOfRecords = generalService.getNoOfRecordsCourses();
//            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//            req.getSession().setAttribute("noOfCoursesPages", noOfPages);
//            req.getSession().setAttribute("currentPage", page);
//
//            req.getSession().setAttribute("categories", generalService.getAllCategories());
//            req.getSession().setAttribute("teachers", generalService.getAllTeachers());
            switch (user.getRole()) {
                case ADMIN -> path = MANAGE_COURSES_ACTION;
                case TEACHER -> path = TEACHER_PAGE;
                case STUDENT -> path = STUDENT_PAGE;
            }
        } catch (ValidateException e) {
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("message", e.getMessage());
            path = AUTH_PAGE;
        }
        return path;
    }
}
