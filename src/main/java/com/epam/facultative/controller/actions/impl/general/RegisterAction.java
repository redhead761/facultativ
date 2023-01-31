package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.REGISTER_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

/**
 * Accessible by general. Allows to add student to database. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class RegisterAction implements Action {
    private final StudentService studentService;

    public RegisterAction(AppContext appContext) {
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
     * @return register page after trying to register
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, STUDENT);
        return REGISTER_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to register.Return input and error message
     * in case if not able.
     *
     * @param req to get parameters and set message in case of successful register
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String password = req.getParameter(PASSWORD);
        String repeatPassword = req.getParameter(REPEAT_PASSWORD);
        StudentDTO student = getStudentForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            studentService.addStudent(student);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(STUDENT, student);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(REGISTER_ACTION);
    }

    private StudentDTO getStudentForAttribute(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        int courseNumber = Integer.parseInt(req.getParameter(COURSE_NUMBER));
        return StudentDTO.builder()
                .id(0)
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .courseNumber(courseNumber)
                .build();
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException(WRONG_REPEAT_PASSWORD);
        }
    }
}
