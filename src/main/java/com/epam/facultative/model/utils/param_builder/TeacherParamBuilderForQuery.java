package com.epam.facultative.model.utils.param_builder;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.*;

/**
 * Class makes it possible to build param for query to obtain sorted, ordered and limited list of events
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class TeacherParamBuilderForQuery extends ParamBuilderForQuery {
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
