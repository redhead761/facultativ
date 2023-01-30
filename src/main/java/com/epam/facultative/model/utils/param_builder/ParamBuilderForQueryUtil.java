package com.epam.facultative.model.utils.param_builder;

/**
 * Factory to return concrete parameter builders
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ParamBuilderForQueryUtil {
    private ParamBuilderForQueryUtil() {
    }

    public static ParamBuilderForQuery courseParamBuilderForQuery() {
        return new CourseParamBuilderForQuery();
    }

    public static ParamBuilderForQuery categoryParamBuilderForQuery() {
        return new CategoryParamBuilderForQuery();
    }

    public static ParamBuilderForQuery teacherParamBuilderForQuery() {
        return new TeacherParamBuilderForQuery();
    }

    public static ParamBuilderForQuery studentParamBuilderForQuery() {
        return new StudentParamBuilderForQuery();
    }

    public static ParamBuilderForQuery userParamBuilderForQuery() {
        return new UserParamBuilderForQuery();
    }
}
