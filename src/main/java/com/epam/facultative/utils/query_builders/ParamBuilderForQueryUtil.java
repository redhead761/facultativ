package com.epam.facultative.utils.query_builders;

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
}
