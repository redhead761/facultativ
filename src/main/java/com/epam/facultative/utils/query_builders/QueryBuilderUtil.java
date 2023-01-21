package com.epam.facultative.utils.query_builders;

public class QueryBuilderUtil {
    private QueryBuilderUtil() {
    }

    public static QueryBuilder courseQueryBuilder() {
        return new CourseQueryBuilder();
    }

    public static QueryBuilder categoryQueryBuilder() {
        return new CategoryQueryBuilder();
    }

    public static QueryBuilder teacherQueryBuilder() {
        return new TeacherQueryBuilder();
    }

    public static QueryBuilder studentQueryBuilder() {
        return new StudentQueryBuilder();
    }
}
