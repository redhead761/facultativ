package com.epam.facultative.utils.query_builders;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;

public class CourseParamBuilderForQuery extends ParamBuilderForQuery {
    private static final Set<String> COURSE_SORT_FIELDS = new HashSet<>();

    static {
        COURSE_SORT_FIELDS.add(COURSE_TITLE);
        COURSE_SORT_FIELDS.add(COURSE_DURATION);
        COURSE_SORT_FIELDS.add(COURSE_START_DATE);
        COURSE_SORT_FIELDS.add(COURSE_AMOUNT_STUDENTS);
    }

    @Override
    protected String checkSortField(String sortField) {
        if (COURSE_SORT_FIELDS.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return null;
    }
}
