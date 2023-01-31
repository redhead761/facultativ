package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.ADD_TEACHER_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.ADD_TEACHER_PAGE;

/**
 * Accessible by admin. Allows to add teacher from database. Implements PRG pattern
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class AddTeacherAction implements Action {
    private final AdminService adminService;

    public AddTeacherAction(AppContext appContext) {
        adminService = appContext.getAdminService();
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
     * @return add teacher page after trying to add teacher
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, TEACHER);
        return ADD_TEACHER_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to add teacher from database.
     * Return input in case if not able
     *
     * @param req to get users id and set message in case of successful added
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String password = req.getParameter(PASSWORD);
        String repeatPassword = req.getParameter(REPEAT_PASSWORD);
        TeacherDTO teacher = getTeacherForAttribute(req);
        try {
            checkConfirmPassword(password, repeatPassword);
            adminService.addTeacher(teacher);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(TEACHER, teacher);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ADD_TEACHER_ACTION);
    }

    private TeacherDTO getTeacherForAttribute(HttpServletRequest req) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String name = req.getParameter(NAME);
        String surname = req.getParameter(SURNAME);
        String email = req.getParameter(EMAIL);
        String degree = req.getParameter(DEGREE);
        return TeacherDTO.builder()
                .id(0)
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .degree(degree)
                .build();
    }

    private void checkConfirmPassword(String password, String repeatPassword) throws ValidateException {
        if (!password.equals(repeatPassword)) {
            throw new ValidateException(WRONG_REPEAT_PASSWORD);
        }
    }
}
