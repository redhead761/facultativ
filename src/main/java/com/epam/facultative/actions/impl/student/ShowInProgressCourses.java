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

public class ShowInProgressCourses implements Action {

    private final StudentService studentService;

    public ShowInProgressCourses() {
        studentService = ServiceFactory.getInstance().getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.getSession().setAttribute("courses", studentService.getCoursesInProgress(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));
        ActionUtils.setUpPaginationStudent(req, studentService, currentPage, recordsPerPage);
        return IN_PROGRESS_COURSES_PAGE;
    }
}
