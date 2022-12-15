package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class ShowGradeListAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        List<UserDTO> students = teacherService.getStudentsByCourse(courseId);
        req.setAttribute("students", students);
        return GRADE_PAGE;
    }
}
