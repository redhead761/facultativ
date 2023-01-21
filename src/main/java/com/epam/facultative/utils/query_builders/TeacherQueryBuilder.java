package com.epam.facultative.utils.query_builders;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;

public class TeacherQueryBuilder extends QueryBuilder {
    private static final Set<String> TEACHER_SORT_FIELDS = new HashSet<>();

    static {
        TEACHER_SORT_FIELDS.add(TEACHER_DEGREE);
        TEACHER_SORT_FIELDS.add(USER_FIRST_NAME);
        TEACHER_SORT_FIELDS.add(USER_FAMILY_NAME);
        TEACHER_SORT_FIELDS.add(USER_LOGIN);
    }

    @Override
    protected String checkSortField(String sortField) {
        if (TEACHER_SORT_FIELDS.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return null;
    }
}
