package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.ActionNameConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.ADD_COURSE_PAGE;
import static com.epam.facultative.model.entities.Status.COMING_SOON;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.categoryParamBuilderForQuery;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

/**
 * Accessible by admin. Allows to add course from database. Implements PRG pattern
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class AddCourseAction implements Action {

    private final AdminService adminService;
    private final GeneralService generalService;

    public AddCourseAction(AppContext appContext) {
        adminService = appContext.getAdminService();
        generalService = appContext.getGeneralService();
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
     * to request. Set all categories to req.
     *
     * @param req to get message attribute from session and put it in request
     * @return add course page after trying to add course
     */
    private String executeGet(HttpServletRequest req) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, COURSE);
        Map.Entry<Integer, List<CategoryDTO>> categories = generalService.getCategories(categoryParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam());
        Map.Entry<Integer, List<TeacherDTO>> teachers = generalService.getTeachers(teacherParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam());
        req.setAttribute(CATEGORIES, categories.getValue());
        req.setAttribute(TEACHERS, teachers.getValue());
        return ADD_COURSE_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to add course from database.
     * Return input in case if not able
     *
     * @param req to get users id and set message in case of successful added
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        CourseDTO course = null;
        try {
            course = getCourseFromParameter(req);
            adminService.addCourse(course);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(COURSE, course);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ADD_COURSE_ACTION);
    }

    private CourseDTO getCourseFromParameter(HttpServletRequest req) throws ServiceException, ValidateException {
        String title = req.getParameter(TITLE);
        int duration = Integer.parseInt(req.getParameter(DURATION));
        LocalDate date = LocalDate.parse(req.getParameter(START_DATE));
        String description = req.getParameter(DESCRIPTION);
        String categoryId = req.getParameter(CATEGORY_ID);
        String teacherId = req.getParameter(TEACHER_ID);
        CategoryDTO categoryDTO = adminService.getCategory(Integer.parseInt(categoryId));
        TeacherDTO teacherDTO = adminService.getTeacher(Integer.parseInt(teacherId));
        return CourseDTO.builder()
                .id(0)
                .title(title)
                .duration(duration)
                .startDate(date)
                .description(description)
                .category(categoryDTO)
                .status(COMING_SOON)
                .teacher(teacherDTO)
                .build();
    }
}

