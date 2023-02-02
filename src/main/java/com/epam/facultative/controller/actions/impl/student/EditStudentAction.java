package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.entities.User;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.COURSE_NUMBER;
import static com.epam.facultative.controller.constants.ActionNameConstants.EDIT_STUDENT_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_STUDENT_PROFILE_PAGE;

/**
 * Accessible by student. Allows to change personal data student in database. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class EditStudentAction implements Action {
    StudentService studentService;

    public EditStudentAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param req to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param req to get message attribute from session and put it in request
     * @return student profile page after trying to edit
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        return EDIT_STUDENT_PROFILE_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to authorization.Return input and error message
     * in case if not able.
     *
     * @param req to get users id and set message in case of successful edit
     * @return path to redirect to executeGet method through front-controller
     */
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
        StudentDTO user = (StudentDTO) req.getSession().getAttribute("user");
        int id = user.getId();
        String login = req.getParameter(LOGIN);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        int courseNumber = Integer.parseInt(req.getParameter(COURSE_NUMBER));
        return StudentDTO.builder()
                .id(id)
                .login(login)
                .name(name)
                .surname(surname)
                .email(email)
                .courseNumber(courseNumber)
                .build();
    }

}
