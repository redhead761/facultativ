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
