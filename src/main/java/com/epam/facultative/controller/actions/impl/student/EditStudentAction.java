package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.AttributeConstants.COURSE_NUMBER;
import static com.epam.facultative.controller.actions.ActionNameConstants.EDIT_STUDENT_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.actions.PageNameConstants.EDIT_STUDENT_PROFILE_PAGE;

public class EditStudentAction implements Action {
    StudentService studentService;

    public EditStudentAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        return EDIT_STUDENT_PROFILE_PAGE;
    }

    private String executePost(HttpServletRequest req) throws ServiceException {
        StudentDTO studentDTO = getStudentForAttribute(req);
        try {
            studentDTO = studentService.updateStudent(studentDTO);
            req.getSession().setAttribute(USER, studentDTO);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(EDIT_STUDENT_ACTION);
    }

    private StudentDTO getStudentForAttribute(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        int courseNumber = Integer.parseInt(req.getParameter(COURSE_NUMBER));
        return StudentDTO.builder()
                .login(login)
                .name(name)
                .surname(surname)
                .email(email)
                .courseNumber(courseNumber)
                .build();
    }

}
