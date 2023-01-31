package com.epam.facultative.controller.actions;

import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.CONTROLLER;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.courseParamBuilderForQuery;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.teacherParamBuilderForQuery;

/**
 * Containing utils methods to use in actions.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ActionUtils {
    private ActionUtils() {
    }

    /**
     * Makes settings for pages with all courses
     *
     * @param req,generalService - derive from action
     */
    public static void setAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);

        String sort = req.getParameter(SORT);
        String order = req.getParameter(ORDER);
        String selectByCategory = req.getParameter(SELECT_BY_CATEGORY);
        String selectByTeacher = req.getParameter(SELECT_BY_TEACHER);

        ParamBuilderForQuery paramBuilderForQuery = courseParamBuilderForQuery().setSortFieldForCourse(sort)
                .setOrder(order)
                .setCategoryFilterForCourse(selectByCategory)
                .setUserIdFilter(selectByTeacher)
                .setLimits(currentPage, recordsPerPage);

        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getCourses(paramBuilderForQuery.getParam());

        setUpPaginate(req, coursesWithRows.getKey());

        req.setAttribute(COURSES, coursesWithRows.getValue());
        req.setAttribute(TEACHERS, generalService.getTeachers(teacherParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam()).getValue());
        req.setAttribute(CATEGORIES, generalService.getCategories(courseParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam()).getValue());
    }

    /**
     * Makes settings for pages with all teacher
     *
     * @param req,generalService - derive from action
     */
    public static void setAllTeachers(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        ParamBuilderForQuery paramBuilderForQuery = teacherParamBuilderForQuery().setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = generalService.getTeachers(paramBuilderForQuery.getParam());
        req.setAttribute(TEACHERS, teachersWithRows.getValue());
        setUpPaginate(req, teachersWithRows.getKey());
    }

    /**
     * Makes settings for pagination
     *
     * @param req         - derive from action
     * @param noOfRecords - number of all records
     */
    public static void setUpPaginate(HttpServletRequest req, int noOfRecords) {
        int currentPage = getCorrectIntValue(req.getParameter(CURRENT_PAGE), 1);
        int recordsPerPage = getCorrectIntValue(req.getParameter(RECORDS_PER_PAGE), 5);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute(NO_OF_PAGES, noOfPages);
        req.setAttribute(CURRENT_PAGE, currentPage);
        req.setAttribute(RECORDS_PER_PAGE, recordsPerPage);

    }

    /**
     * Checks the value limit or offset and return it. Return default if value invalid
     *
     * @param value        - value
     * @param defaultValue - default value
     * @return int - correct value
     */
    static int getCorrectIntValue(String value, int defaultValue) {
        try {
            int i = Integer.parseInt(value);
            if (i <= 0) {
                return defaultValue;
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * Creates get action with parameters
     *
     * @param action     - Action to be sent
     * @param parameters - required parameters
     * @return - path
     */
    public static String getGetAction(String action, String... parameters) {
        String base = CONTROLLER + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "").setEmptyValue("");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + stringJoiner;
    }

    /**
     * Checks if method is POST method
     *
     * @param req passed by action
     * @return true if POST method
     */
    public static boolean isPostMethod(HttpServletRequest req) {
        return req.getMethod().equals("POST");
    }

    /**
     * Transfers sessions attributes to request.Then delete
     *
     * @param req passed by action
     */
    public static void transferAttributeFromSessionToRequest(HttpServletRequest req, String... attributeNames) {
        for (String attributeName : attributeNames) {
            Object obj = req.getSession().getAttribute(attributeName);
            req.setAttribute(attributeName, obj);
            req.getSession().removeAttribute(attributeName);
        }
    }
}
