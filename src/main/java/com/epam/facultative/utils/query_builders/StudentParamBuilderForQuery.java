package com.epam.facultative.utils.query_builders;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.USER_LOGIN;

public class StudentParamBuilderForQuery extends ParamBuilderForQuery {
    private static final Set<String> STUDENT_SORT_FIELDS = new HashSet<>();
    static {
        STUDENT_SORT_FIELDS.add(STUDENT_COURSE_NUMBER);
        STUDENT_SORT_FIELDS.add(USER_FIRST_NAME);
        STUDENT_SORT_FIELDS.add(USER_FAMILY_NAME);
        STUDENT_SORT_FIELDS.add(USER_LOGIN);
    }
    @Override
    protected String checkSortField(String sortField) {
        if (STUDENT_SORT_FIELDS.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return null;
    }
}
