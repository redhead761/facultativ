package com.epam.facultative.model.utils.param_builder;

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
