package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowTeacherCoursesAction implements Action {
    private final TeacherService teacherService;

    public ShowTeacherCoursesAction() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.setAttribute("courses", teacherService.getTeacherCourses(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = teacherService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfCoursesPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
        return TEACHER_COURSES_PAGE;
    }
}
