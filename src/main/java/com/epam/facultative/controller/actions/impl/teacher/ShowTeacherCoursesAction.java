package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowTeacherCoursesAction implements Action {
    private final TeacherService teacherService;

    public ShowTeacherCoursesAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.setAttribute("courses", teacherService.getTeacherCourses(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = teacherService.getNoOfRecordsCourses();
        ActionUtils.setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        return TEACHER_COURSES_PAGE;
    }
}
