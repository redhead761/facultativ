package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.entities.Status;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.facultative.controller.constants.ActionNameConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_COURSE_PAGE;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.categoryParamBuilderForQuery;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

/**
 * Accessible by admin. Allows to update course from database. Implements PRG pattern
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class UpdateCourseAction implements Action {
    private final AdminService adminService;
    private final GeneralService generalService;
    private final AppContext appContext;

    public UpdateCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
        this.appContext = appContext;
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
     * @return update course page after trying to update course
     */
    private String executeGet(HttpServletRequest req) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        String courseId = req.getParameter(COURSE_ID);
        CourseDTO course = null;
        try {
            course = adminService.getCourse(Integer.parseInt(courseId));
        } catch (ValidateException e) {
            req.setAttribute(MESSAGE, e.getMessage());
        }
        req.setAttribute(COURSE, course);
        req.setAttribute(CATEGORIES, generalService.getCategories(categoryParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam()).getValue());
        req.setAttribute(TEACHERS, generalService.getTeachers(teacherParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam()).getValue());
        return EDIT_COURSE_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to update course from database.
     * Return input in case if not able
     *
     * @param req to get users id and set message in case of successful updated
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String courseId = req.getParameter(COURSE_ID);
        try {
            CourseDTO course = getCourseFromParameter(req);
            adminService.updateCourse(course);
            if (req.getParameter(EMAIL_SEND) != null) {
                adminService.courseLaunchNewsLetter(course, appContext);
            }
            req.getSession().setAttribute(MESSAGE, CHANGES_SAVED);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(UPDATE_COURSE_ACTION, COURSE_ID, courseId);
    }

    private CourseDTO getCourseFromParameter(HttpServletRequest req) throws ServiceException, ValidateException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        String title = req.getParameter(TITLE);
        int duration = Integer.parseInt(req.getParameter(DURATION));
        LocalDate date = LocalDate.parse(req.getParameter(START_DATE));
        String description = req.getParameter(DESCRIPTION);
        Status status = Status.valueOf(req.getParameter(STATUS));
        String categoryId = req.getParameter(CATEGORY_ID);
        String teacherId = req.getParameter(TEACHER_ID);
        CategoryDTO categoryDTO = adminService.getCategory(Integer.parseInt(categoryId));
        TeacherDTO teacherDTO = adminService.getTeacher(Integer.parseInt(teacherId));
        return CourseDTO.builder()
                .id(courseId)
                .title(title)
                .duration(duration)
                .startDate(date)
                .description(description)
                .category(categoryDTO)
                .status(status)
                .teacher(teacherDTO)
                .build();
    }
}

