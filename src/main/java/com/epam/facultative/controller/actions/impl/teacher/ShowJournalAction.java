package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import com.epam.facultative.utils.query_builders.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.testSetUp;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.actions.PageNameConstants.*;
import static com.epam.facultative.utils.query_builders.ParamBuilderForQueryUtil.courseParamBuilderForQuery;

public class ShowJournalAction implements Action {
    private final TeacherService teacherService;

    public ShowJournalAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        String courseId = req.getParameter(COURSE_ID);
        ParamBuilderForQuery paramBuilder = courseParamBuilderForQuery()
                .setIdFilterForCourse(courseId)
                .setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<StudentDTO>> studentsWithRows = teacherService.getStudentsByCourse(paramBuilder.getParam());
        req.setAttribute(STUDENTS, studentsWithRows.getValue());
        testSetUp(req, studentsWithRows.getKey());
        return JOURNAL_PAGE;
    }
}
