package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.AttributeConstants.*;
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
        TeacherDTO teacher = (TeacherDTO) req.getSession().getAttribute(USER);
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = teacherService.getTeacherCourses(teacher.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage);
        req.setAttribute(COURSES, coursesWithRows.getValue());
        int noOfRecords = coursesWithRows.getKey();
        ActionUtils.setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        return TEACHER_COURSES_PAGE;
    }
}
