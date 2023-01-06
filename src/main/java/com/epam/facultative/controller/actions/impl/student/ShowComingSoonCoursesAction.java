package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowComingSoonCoursesAction implements Action {
    private final StudentService studentService;

    public ShowComingSoonCoursesAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.setAttribute("courses", studentService.getCoursesComingSoon(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));
        ActionUtils.setUpPaginationStudent(req, studentService, currentPage, recordsPerPage);
        return COMING_SOON_COURSES_PAGE;
    }
}
