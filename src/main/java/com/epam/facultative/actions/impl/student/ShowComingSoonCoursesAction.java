package com.epam.facultative.actions.impl.student;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.Constants.*;

public class ShowComingSoonCoursesAction implements Action {
    private final StudentService studentService;

    public ShowComingSoonCoursesAction() {
        studentService = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.getSession().setAttribute("courses", studentService.getCoursesComingSoon(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));

        int noOfRecords = studentService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCoursesPages", noOfPages);
        req.getSession().setAttribute("currentPage", currentPage);
        return COMING_SOON_COURSES_PAGE;
    }
}
